package com.yaho.seungminonsale.project.service;

import com.yaho.seungminonsale.project.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("프로젝트 생성 요청 없으면 예외 발생")
    void createProjectWhenRequestIsNullThrowsException() {
        assertThatThrownBy(() -> projectService.createProject(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름과 설명은 필수입니다.");

        verifyNoInteractions(projectRepository);
    }
}