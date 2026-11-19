package com.yaho.seungminonsale.teammembership.service;

import com.yaho.seungminonsale.exception.MembershipAccessDeniedException;
import com.yaho.seungminonsale.exception.ProjectNotFoundException;
import com.yaho.seungminonsale.exception.UserNotFoundException;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.project.repository.ProjectRepository;
import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;
import com.yaho.seungminonsale.teammembership.repository.TeamMembershipRepository;
import com.yaho.seungminonsale.user.entity.Role;
import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.user.repository.UserRepository;
import com.yaho.seungminonsale.worker.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMembershipServiceImpl implements TeamMembershipService {

    private final TeamMembershipRepository teamMembershipRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final WorkerRepository workerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TeamMembershipResponse> getMemberships(Long userId, Long projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (projectId != null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(ProjectNotFoundException::new);

            if (!isAdmin && !project.getOwner().getId().equals(userId))
                throw new MembershipAccessDeniedException();

            return toResponses(teamMembershipRepository.findAllByProject_Id(projectId));
        }

        if (isAdmin)
            return toResponses(teamMembershipRepository.findAllWithProjectAndWorker());

        return workerRepository.findByUser_Id(userId)
                .map(worker -> toResponses(teamMembershipRepository.findAllByWorker_Id(worker.getId())))
                .orElseGet(List::of);
    }

    private List<TeamMembershipResponse> toResponses(List<TeamMembership> memberships) {
        return memberships.stream()
                .map(TeamMembershipResponse::from)
                .toList();
    }
}
