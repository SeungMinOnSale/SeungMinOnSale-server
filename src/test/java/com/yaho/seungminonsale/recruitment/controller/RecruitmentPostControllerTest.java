package com.yaho.seungminonsale.recruitment.controller;

import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostCreateRequest;
import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostResponse;
import com.yaho.seungminonsale.recruitment.entity.RecruitmentStatus;
import com.yaho.seungminonsale.recruitment.service.RecruitmentPostService;
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
    @DisplayName("모집글 생성 성공 시 HTTP 201을 반환한다")
    void createRecruitmentPostReturns201() throws Exception {
        Long projectId = 1L;

        RecruitmentPostResponse response = new RecruitmentPostResponse(
                10L,
                projectId,
                "백엔드 개발자 모집",
                "백엔드",
                "Java, Spring Boot",
                2,
                RecruitmentStatus.OPEN
        );

        given(recruitmentPostService.createRecruitmentPost(
                any(RecruitmentPostCreateRequest.class),
                eq(projectId)
        )).willReturn(response);

        mockMvc.perform(post("/api/projects/{projectId}/recruitment-posts", projectId)
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

        verify(recruitmentPostService).createRecruitmentPost(
                any(RecruitmentPostCreateRequest.class),
                eq(projectId)
        );
    }
}