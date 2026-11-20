package com.yaho.seungminonsale.project.service;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;
import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.project.entity.ProjectStatus;
import com.yaho.seungminonsale.project.repository.ProjectRepository;
import com.yaho.seungminonsale.user.entity.User;
import com.yaho.seungminonsale.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User owner;

    @Test
    @DisplayName("프로젝트 생성 시 저장하고 프로젝트 정보를 반환한다")
    void createProjectSuccessfully() {
        ProjectCreateRequest request = new ProjectCreateRequest(
                "팀 프로젝트",
                "Spring Boot 기반 프로젝트입니다."
        );

        given(owner.getId()).willReturn(1L);
        given(userRepository.findById(1L))
                .willReturn(Optional.of(owner));

        Project savedProject = new Project(
                10L,
                owner,
                request.name(),
                request.description(),
                ProjectStatus.ONGOING
        );

        given(projectRepository.save(any(Project.class)))
                .willReturn(savedProject);

        ProjectResponse response =
                projectService.createProject(request, 1L);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.ownerId()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.description()).isEqualTo(request.description());
        assertThat(response.status()).isEqualTo(ProjectStatus.ONGOING);

        ArgumentCaptor<Project> captor =
                ArgumentCaptor.forClass(Project.class);

        verify(projectRepository).save(captor.capture());

        Project project = captor.getValue();

        assertThat(project.getId()).isNull();
        assertThat(project.getOwner()).isSameAs(owner);
        assertThat(project.getName()).isEqualTo(request.name());
        assertThat(project.getDescription()).isEqualTo(request.description());
        assertThat(project.getStatus()).isEqualTo(ProjectStatus.ONGOING);
    }

    @Test
    @DisplayName("프로젝트 생성 요청이 없으면 예외가 발생한다")
    void createProjectWhenRequestIsNullThrowsException() {
        assertThatThrownBy(() -> projectService.createProject(null, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로젝트 이름과 설명은 필수입니다.");

        verifyNoInteractions(projectRepository, userRepository);
    }
}