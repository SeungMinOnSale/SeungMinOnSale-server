package com.yaho.seungminonsale.projectresult.service;

import com.yaho.seungminonsale.exception.NotProjectOwnerException;
import com.yaho.seungminonsale.exception.ProjectNotFoundException;
import com.yaho.seungminonsale.exception.ProjectNotOngoingException;
import com.yaho.seungminonsale.exception.ProjectResultAlreadyExistsException;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.project.repository.ProjectRepository;
import com.yaho.seungminonsale.projectresult.domain.entity.ProjectResult;
import com.yaho.seungminonsale.projectresult.dto.ProjectResultCreateRequest;
import com.yaho.seungminonsale.projectresult.dto.ProjectResultResponse;
import com.yaho.seungminonsale.projectresult.repository.ProjectResultRepository;
import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import com.yaho.seungminonsale.teammembership.domain.enums.MembershipStatus;
import com.yaho.seungminonsale.teammembership.repository.TeamMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectResultServiceImpl implements ProjectResultService {

    private final ProjectResultRepository projectResultRepository;
    private final ProjectRepository projectRepository;
    private final TeamMembershipRepository teamMembershipRepository;

    @Override
    @Transactional
    public ProjectResultResponse createProjectResult(Long userId, ProjectResultCreateRequest request) {
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(ProjectNotFoundException::new);

        if (!project.getOwner().getId().equals(userId))
            throw new NotProjectOwnerException();

        if (!project.isOngoing())
            throw new ProjectNotOngoingException();

        if (projectResultRepository.existsByProject_Id(project.getId()))
            throw new ProjectResultAlreadyExistsException();

        ProjectResult projectResult = projectResultRepository.save(
                ProjectResult.create(project, request.summary(), request.repositoryUrl()));

        project.complete();
        teamMembershipRepository.findAllByProject_IdAndStatus(project.getId(), MembershipStatus.ACTIVE)
                .forEach(TeamMembership::complete);

        return ProjectResultResponse.from(projectResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResultResponse> getProjectResults(Long projectId) {
        if (projectId != null)
            return projectResultRepository.findByProject_Id(projectId).stream()
                    .map(ProjectResultResponse::from)
                    .toList();

        return projectResultRepository.findAllWithProject().stream()
                .map(ProjectResultResponse::from)
                .toList();
    }
}
