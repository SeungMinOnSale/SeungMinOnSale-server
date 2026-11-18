
package com.yaho.seungminonsale.project.service;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.repository.ProjectRepository;
import com.yaho.seungminonsale.user.entity.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void createProjectInvalidNameTest() {

        ProjectCreateRequest request =
                new ProjectCreateRequest("", "설명");

        User owner = mock(User.class);

        assertThatThrownBy(() ->
                projectService.createProject(request, owner)
        ).isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(projectRepository);
    }
}
