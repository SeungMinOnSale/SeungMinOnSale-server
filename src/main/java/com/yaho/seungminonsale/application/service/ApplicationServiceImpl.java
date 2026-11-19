package com.yaho.seungminonsale.application.service;

import com.yaho.seungminonsale.application.entity.Application;
import com.yaho.seungminonsale.application.entity.ApplicationStatus;
import com.yaho.seungminonsale.application.repository.ApplicationRepository;
import com.yaho.seungminonsale.exception.AlreadyTeamMemberException;
import com.yaho.seungminonsale.exception.ApplicationAlreadyDecidedException;
import com.yaho.seungminonsale.exception.ApplicationNotFoundException;
import com.yaho.seungminonsale.exception.InvalidDecisionStatusException;
import com.yaho.seungminonsale.exception.NotProjectOwnerException;
import com.yaho.seungminonsale.exception.RecruitmentCapacityExceededException;
import com.yaho.seungminonsale.exception.RecruitmentNotAvailableException;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.recruitmentPost.entity.RecruitmentPost;
import com.yaho.seungminonsale.recruitmentPost.repository.RecruitmentPostRepository;
import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import com.yaho.seungminonsale.application.dto.ApplicationDecisionRequest;
import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;
import com.yaho.seungminonsale.teammembership.repository.TeamMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final TeamMembershipRepository teamMembershipRepository;

    @Override
    @Transactional
    public TeamMembershipResponse decideApplication(Long userId, Long applicationId, ApplicationDecisionRequest request) {
        ApplicationStatus decision = request.status();

        if (decision == null || decision == ApplicationStatus.PENDING)
            throw new InvalidDecisionStatusException();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
        RecruitmentPost recruitmentPost = application.getRecruitmentPost();
        Project project = recruitmentPost.getProject();

        if (!project.getOwner().getId().equals(userId))
            throw new NotProjectOwnerException();

        if (!application.isPending())
            throw new ApplicationAlreadyDecidedException();

        if (decision == ApplicationStatus.REJECTED) {
            application.reject();
            return null;
        }

        if (!recruitmentPost.isOpen() || !project.isOngoing())
            throw new RecruitmentNotAvailableException();

        RecruitmentPost lockedPost = recruitmentPostRepository.findWithLockById(recruitmentPost.getId())
                .orElseThrow(RecruitmentNotAvailableException::new);
        long acceptedCount = applicationRepository.countByRecruitmentPost_IdAndStatus(
                lockedPost.getId(), ApplicationStatus.ACCEPTED);
        if (acceptedCount >= lockedPost.getCapacity())
            throw new RecruitmentCapacityExceededException();

        if (teamMembershipRepository.existsByProject_IdAndWorker_Id(project.getId(), application.getWorker().getId()))
            throw new AlreadyTeamMemberException();

        application.accept();
        TeamMembership membership = teamMembershipRepository.save(TeamMembership.create(application));

        return TeamMembershipResponse.from(membership);
    }
}
