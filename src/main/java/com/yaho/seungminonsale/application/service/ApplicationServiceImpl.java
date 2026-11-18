package com.yaho.seungminonsale.application.service;

import com.yaho.seungminonsale.application.dto.ApplicationDecisionRequest;
import com.yaho.seungminonsale.application.dto.ApplicationDecisionResponse;
import com.yaho.seungminonsale.application.entity.Application;
import com.yaho.seungminonsale.application.entity.ApplicationStatus;
import com.yaho.seungminonsale.application.repository.ApplicationRepository;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.project.entity.ProjectStatus;
import com.yaho.seungminonsale.recruitment.entity.RecruitmentPost;
import com.yaho.seungminonsale.recruitment.entity.RecruitmentStatus;
import com.yaho.seungminonsale.recruitment.repository.RecruitmentPostRepository;
import com.yaho.seungminonsale.teammembership.repository.TeamMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final TeamMembershipRepository teamMembershipRepository;

    @Override
    @Transactional
    public ApplicationDecisionResponse decideApplication(Long id, Long userId, ApplicationDecisionRequest request) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        RecruitmentPost recruitmentPost = application.getRecruitmentPost();

        Project project = recruitmentPost.getProject();

        if (!project.getOwner().getId().equals(userId))
            throw new RuntimeException();

        if (!application.getStatus().equals(ApplicationStatus.PENDING))
            throw new RuntimeException();

        if (!(recruitmentPost.getStatus().equals(RecruitmentStatus.OPEN) &&
                project.getStatus().equals(ProjectStatus.ONGOING)))
            throw new RuntimeException();



        Integer acceptedRecruitmentPosts = recruitmentPostRepository.countByIdForUpdate(recruitmentPost.getId();

        if (acceptedRecruitmentPosts > recruitmentPost.getCapacity())
            throw new RuntimeException();

        Long projectId = recruitmentPost.getProject().getId();
        Long workerId = application.getWorker().getId();

        if (teamMembershipRepository.existsByProject_IdAndWorker_Id(projectId, workerId))
            throw new RuntimeException();
    }
}
