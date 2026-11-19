package com.yaho.seungminonsale.project.controller;

import com.yaho.seungminonsale.project.service.ProjectService;
import com.yaho.seungminonsale.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private User owner;

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
    @DisplayName("프로젝트 생성 성공 시 HTTP 201을 반환한다")
    void createProjectReturns201() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/api/projects")
                        .requestAttr("currentUser", owner)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "테스트",
                                    "description": "flsnglf"
                                }
                                """));
        resultActions.andDo(print())
                .andExpect(status().is(201));
//                .andExpect(status().isCreated());

//                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("프로젝트 생성 성공 시 HTTP 400을 반환한다")
    void createProjectReturns400() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/api/projects")
                // 임의적으로 에러를 발생하기 위해서 데이터 보내지 않음
//                        .requestAttr("currentUser", owner)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "name": "테스트",
                                    "description": "flsnglf"
                                }
                                """));
        resultActions.andDo(print())
                .andExpect(status().is(400));
//                .andExpect(status().isCreated());

    }
}