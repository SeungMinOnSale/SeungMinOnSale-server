package com.yaho.seungminonsale.projectresult.controller;

import com.yaho.seungminonsale.exception.ProjectNotFoundException;
import com.yaho.seungminonsale.exception.ProjectResultAlreadyExistsException;
import com.yaho.seungminonsale.projectresult.dto.ProjectResultCreateRequest;
import com.yaho.seungminonsale.projectresult.dto.ProjectResultResponse;
import com.yaho.seungminonsale.projectresult.service.ProjectResultService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectResultController.class)
class ProjectResultControllerTest {

    private static final String url = "/api/project-results";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectResultService projectResultService;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @DisplayName("프로젝트 결과 등록 성공 - 201")
    void createProjectResult_success() throws Exception {
        // given
        ProjectResultCreateRequest request = new ProjectResultCreateRequest(1L, "더미", "url");
        ProjectResultResponse response = new ProjectResultResponse(
                100L, 1L, "더미", "더미", "url", LocalDateTime.of(2026, 11, 19, 10, 0));
        BDDMockito.given(projectResultService.createProjectResult(
                        BDDMockito.eq(10L), BDDMockito.any(ProjectResultCreateRequest.class)))
                .willReturn(response);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.summary").value("더미"))
                .andExpect(jsonPath("$.repositoryUrl").value("url"));
    }

    @Test
    @DisplayName("프로젝트 결과 등록 실패 - 400")
    void createProjectResult_missingUserId() throws Exception {
        // given
        ProjectResultCreateRequest request = new ProjectResultCreateRequest(1L, "더미", "url");

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.errors[0].field").value("userId"));
    }

    @Test
    @DisplayName("프로젝트 결과 등록 실패 - 404")
    void createProjectResult_projectNotFound() throws Exception {
        // given
        ProjectResultCreateRequest request = new ProjectResultCreateRequest(1L, "더미", "url");
        BDDMockito.given(projectResultService.createProjectResult(
                        BDDMockito.eq(10L), BDDMockito.any(ProjectResultCreateRequest.class)))
                .willThrow(new ProjectNotFoundException());

        // when
        ResultActions result = mockMvc.perform(post(url)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PROJECT_NOT_FOUND"));
    }

    @Test
    @DisplayName("프로젝트 결과 등록 실패 - 409")
    void createProjectResult_alreadyExists() throws Exception {
        // given
        ProjectResultCreateRequest request = new ProjectResultCreateRequest(1L, "더미", "url");
        BDDMockito.given(projectResultService.createProjectResult(
                        BDDMockito.eq(10L), BDDMockito.any(ProjectResultCreateRequest.class)))
                .willThrow(new ProjectResultAlreadyExistsException());

        // when
        ResultActions result = mockMvc.perform(post(url)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("PROJECT_RESULT_ALREADY_EXISTS"));
    }

    @Test
    @DisplayName("프로젝트 결과 조회 성공 - 200")
    void getProjectResults_success() throws Exception {
        // given
        ProjectResultResponse response = new ProjectResultResponse(
                100L, 1L, "더미", "더미", "url", LocalDateTime.of(2026, 11, 19, 10, 0));
        BDDMockito.given(projectResultService.getProjectResults(1L))
                .willReturn(List.of(response));

        // when
        ResultActions result = mockMvc.perform(get(url)
                .param("projectId", String.valueOf(1L)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[0].summary").value("더미"))
                .andExpect(jsonPath("$[0].repositoryUrl").value("url"));
    }

    @Test
    @DisplayName("프로젝트 결과 조회 실패 - 400")
    void getProjectResults_invalidProjectId() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get(url)
                .param("projectId", "abc"));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.errors[0].field").value("projectId"));
    }
}
