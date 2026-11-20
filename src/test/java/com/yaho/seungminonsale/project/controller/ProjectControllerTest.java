package com.yaho.seungminonsale.project.controller;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;
import com.yaho.seungminonsale.project.entity.ProjectStatus;
import com.yaho.seungminonsale.project.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(projectController)
                .build();
    }

    @Test
    @DisplayName("프로젝트 생성 성공 시 HTTP 201과 프로젝트 정보를 반환한다")
    void createProjectReturns201() throws Exception {
        ProjectResponse response = new ProjectResponse(
                10L,
                1L,
                "팀 프로젝트",
                "Spring Boot 기반 프로젝트입니다.",
                ProjectStatus.ONGOING
        );

        given(projectService.createProject(
                any(ProjectCreateRequest.class),
                eq(1L)
        )).willReturn(response);

        mockMvc.perform(post("/api/projects")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "팀 프로젝트",
                                    "description": "Spring Boot 기반 프로젝트입니다."
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.ownerId").value(1))
                .andExpect(jsonPath("$.name").value("팀 프로젝트"))
                .andExpect(jsonPath("$.description")
                        .value("Spring Boot 기반 프로젝트입니다."))
                .andExpect(jsonPath("$.status").value("ONGOING"));

        verify(projectService).createProject(
                any(ProjectCreateRequest.class),
                eq(1L)
        );
    }

    @Test
    @DisplayName("userId가 없으면 HTTP 400을 반환한다")
    void createProjectWithoutCurrentUserReturns400() throws Exception {
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "팀 프로젝트",
                                    "description": "Spring Boot 기반 프로젝트입니다."
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(projectService);
    }
}