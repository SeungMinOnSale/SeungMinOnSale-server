
package com.yaho.seungminonsale.project.service;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;

import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.project.entity.ProjectStatus;
import com.yaho.seungminonsale.project.repository.ProjectRepository;
import com.yaho.seungminonsale.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// User 클래스는 A 파트의 실제 패키지에서 import

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public ProjectResponse createProject(
            ProjectCreateRequest request,
            User owner
    ) {

        // 1. 요청 데이터 검증
        if (request == null
                || request.name() == null
                || request.name().isBlank()
                || request.description() == null
                || request.description().isBlank()) {
            throw new IllegalArgumentException("프로젝트 이름과 설명은 필수입니다.");
        }

        // 2. 프로젝트 개설자 확인
        if (owner == null || owner.getId() == null) {
            throw new IllegalArgumentException("프로젝트 개설자 정보가 없습니다.");
        }

        // 3. Project Entity 생성
        Project project = new Project();

        project.setOwner(owner);
        project.setName(request.name());
        project.setDescription(request.description());
        project.setStatus(ProjectStatus.ONGOING);

        // 4. DB에 프로젝트 저장
        Project savedProject = projectRepository.save(project);

        // 5. 응답 DTO 생성
        return new ProjectResponse(
                savedProject.getId(),
                savedProject.getOwner().getId(),
                savedProject.getName(),
                savedProject.getDescription(),
                savedProject.getStatus(),
                savedProject.getCreatedAt()
        );
    }
}
