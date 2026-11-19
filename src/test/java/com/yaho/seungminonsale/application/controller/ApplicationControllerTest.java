package com.yaho.seungminonsale.application.controller;

import com.yaho.seungminonsale.application.dto.ApplicationDecisionRequest;
import com.yaho.seungminonsale.application.entity.ApplicationStatus;
import com.yaho.seungminonsale.application.service.ApplicationService;
import com.yaho.seungminonsale.exception.AlreadyTeamMemberException;
import com.yaho.seungminonsale.exception.ApplicationNotFoundException;
import com.yaho.seungminonsale.exception.NotProjectOwnerException;
import com.yaho.seungminonsale.teammembership.domain.enums.MembershipStatus;
import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    private static final String url = "/api/applications/{id}/decision";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @DisplayName("지원 승인 성공 - 200")
    void decideApplication_accept() throws Exception {
        // given
        ApplicationDecisionRequest request = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED);
        TeamMembershipResponse response = new TeamMembershipResponse(
                100L, 2L, "더미", 3L, "더미", MembershipStatus.ACTIVE, LocalDateTime.of(2026, 11, 19, 10, 0));
        BDDMockito.given(applicationService.decideApplication(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ApplicationDecisionRequest.class)))
                .willReturn(response);

        // when
        ResultActions result = mockMvc.perform(put(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.projectId").value(2))
                .andExpect(jsonPath("$.workerId").value(3))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("지원 거절 성공 - 200")
    void decideApplication_reject() throws Exception {
        // given
        ApplicationDecisionRequest request = new ApplicationDecisionRequest(ApplicationStatus.REJECTED);
        BDDMockito.given(applicationService.decideApplication(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ApplicationDecisionRequest.class)))
                .willReturn(null);

        // when
        ResultActions result = mockMvc.perform(put(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("지원 처리 실패 - 400")
    void decideApplication_missingUserId() throws Exception {
        // given
        ApplicationDecisionRequest request = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED);

        // when
        ResultActions result = mockMvc.perform(put(url, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.errors[0].field").value("userId"));
    }

    @Test
    @DisplayName("지원 처리 실패 - 404")
    void decideApplication_applicationNotFound() throws Exception {
        // given
        ApplicationDecisionRequest request = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED);
        BDDMockito.given(applicationService.decideApplication(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ApplicationDecisionRequest.class)))
                .willThrow(new ApplicationNotFoundException());

        // when
        ResultActions result = mockMvc.perform(put(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("APPLICATION_NOT_FOUND"));
    }

    @Test
    @DisplayName("지원 처리 실패 - 403")
    void decideApplication_notProjectOwner() throws Exception {
        // given
        ApplicationDecisionRequest request = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED);
        BDDMockito.given(applicationService.decideApplication(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ApplicationDecisionRequest.class)))
                .willThrow(new NotProjectOwnerException());

        // when
        ResultActions result = mockMvc.perform(put(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("NOT_PROJECT_OWNER"));
    }

    @Test
    @DisplayName("지원 처리 실패 - 409")
    void decideApplication_alreadyTeamMember() throws Exception {
        // given
        ApplicationDecisionRequest request = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED);
        BDDMockito.given(applicationService.decideApplication(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ApplicationDecisionRequest.class)))
                .willThrow(new AlreadyTeamMemberException());

        // when
        ResultActions result = mockMvc.perform(put(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ALREADY_TEAM_MEMBER"));
    }
}
