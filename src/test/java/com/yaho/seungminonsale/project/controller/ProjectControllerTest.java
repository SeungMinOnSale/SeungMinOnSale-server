
package com.yaho.seungminonsale.project.controller;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;
import com.yaho.seungminonsale.project.entity.ProjectStatus;
import com.yaho.seungminonsale.project.service.ProjectService;
import com.yaho.seungminonsale.user.entity.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ProjectController(projectService))
                .build();
    }

    @Test
    void createProjectTest() throws Exception {

        User owner = mock(User.class);

        ProjectResponse response = new ProjectResponse(
                1L,
                3L,
                "프로젝트",
                "설명",
                ProjectStatus.ONGOING,
                LocalDateTime.of(2026, 11, 18, 12, 0)
        );

        when(projectService.createProject(
                any(ProjectCreateRequest.class),
                same(owner)
        )).thenReturn(response);

        mockMvc.perform(post("/api/projects")
                        .requestAttr("currentUser", owner)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "AI 프로젝트",
                                "description": "프로젝트 설명"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("AI 프로젝트"))
                .andExpect(jsonPath("$.status").value("ONGOING"));

        verify(projectService).createProject(
                any(ProjectCreateRequest.class),
                same(owner)
        );
    }
}
