package com.yaho.seungminonsale.project.service;

import com.yaho.seungminonsale.exception.UserNotFoundException;
import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.project.entity.ProjectStatus;
import com.yaho.seungminonsale.project.repository.ProjectRepository;
import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ProjectResponse createProject(
            ProjectCreateRequest request,
            Long userId
    ) {
        if (request == null ||
                request.name() == null ||
                request.name().isBlank() ||
                request.description() == null ||
                request.description().isBlank()) {
            throw new IllegalArgumentException(
                    "프로젝트 이름과 설명은 필수입니다."
            );
        }

        if (userId == null) {
            throw new IllegalArgumentException(
                    "프로젝트 개설자 정보가 없습니다."
            );
        }

        User owner = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Project project = new Project(
                null,
                owner,
                request.name(),
                request.description(),
                ProjectStatus.ONGOING
        );

        Project savedProject = projectRepository.save(project);

        return new ProjectResponse(
                savedProject.getId(),
                savedProject.getOwner().getId(),
                savedProject.getName(),
                savedProject.getDescription(),
                savedProject.getStatus()
        );
    }
}