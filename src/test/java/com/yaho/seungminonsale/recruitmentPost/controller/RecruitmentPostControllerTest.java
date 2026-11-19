package com.yaho.seungminonsale.recruitmentPost.controller;

import com.yaho.seungminonsale.recruitmentPost.service.RecruitmentPostService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RecruitmentPostControllerTest {

    @Mock
    private RecruitmentPostService recruitmentPostService;

    @InjectMocks
    private RecruitmentPostController recruitmentPostController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(recruitmentPostController)
                .build();
    }

    @Test
    @DisplayName("모집글 생성 성공 시 HTTP 201을 반환")
    void createRecruitmentPostReturns201() throws Exception {
        mockMvc.perform(post("/api/projects/{projectId}/recruitment-posts", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "백엔드 개발자 모집",
                                    "position": "백엔드",
                                    "requiredSkills": "Java, Spring Boot",
                                    "capacity": 2
                                }
                                """))
                .andExpect(status().isCreated());
    }
}