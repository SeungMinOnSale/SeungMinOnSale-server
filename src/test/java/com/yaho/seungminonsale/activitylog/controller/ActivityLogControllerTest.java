package com.yaho.seungminonsale.activitylog.controller;

import com.yaho.seungminonsale.activitylog.dto.ActivityLogCreateRequest;
import com.yaho.seungminonsale.activitylog.dto.ActivityLogResponse;
import com.yaho.seungminonsale.activitylog.service.ActivityLogService;
import com.yaho.seungminonsale.exception.MembershipNotFoundException;
import com.yaho.seungminonsale.exception.NotMembershipOwnerException;
import com.yaho.seungminonsale.exception.ProjectNotOngoingException;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActivityLogController.class)
class ActivityLogControllerTest {

    private static final String url = "/api/memberships/{id}/activities";
    private static final LocalDate ACTIVITY_DATE = LocalDate.of(2026, 11, 19);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ActivityLogService activityLogService;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @DisplayName("활동 기록 성공 - 201")
    void createActivityLog_success() throws Exception {
        // given
        ActivityLogCreateRequest request = new ActivityLogCreateRequest("더미", ACTIVITY_DATE);
        ActivityLogResponse response = new ActivityLogResponse(
                100L, 1L, "더미", ACTIVITY_DATE, LocalDateTime.of(2026, 11, 19, 10, 0));
        BDDMockito.given(activityLogService.createActivityLog(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ActivityLogCreateRequest.class)))
                .willReturn(response);

        // when
        ResultActions result = mockMvc.perform(post(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.membershipId").value(1))
                .andExpect(jsonPath("$.content").value("더미"))
                .andExpect(jsonPath("$.activityDate").value("2026-11-19"));
    }

    @Test
    @DisplayName("활동 기록 실패 - 400")
    void createActivityLog_missingUserId() throws Exception {
        // given
        ActivityLogCreateRequest request = new ActivityLogCreateRequest("더미", ACTIVITY_DATE);

        // when
        ResultActions result = mockMvc.perform(post(url, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.errors[0].field").value("userId"));
    }

    @Test
    @DisplayName("활동 기록 실패 - 404")
    void createActivityLog_membershipNotFound() throws Exception {
        // given
        ActivityLogCreateRequest request = new ActivityLogCreateRequest("더미", ACTIVITY_DATE);
        BDDMockito.given(activityLogService.createActivityLog(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ActivityLogCreateRequest.class)))
                .willThrow(new MembershipNotFoundException());

        // when
        ResultActions result = mockMvc.perform(post(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("MEMBERSHIP_NOT_FOUND"));
    }

    @Test
    @DisplayName("활동 기록 실패 - 403")
    void createActivityLog_notMembershipOwner() throws Exception {
        // given
        ActivityLogCreateRequest request = new ActivityLogCreateRequest("더미", ACTIVITY_DATE);
        BDDMockito.given(activityLogService.createActivityLog(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ActivityLogCreateRequest.class)))
                .willThrow(new NotMembershipOwnerException());

        // when
        ResultActions result = mockMvc.perform(post(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("NOT_MEMBERSHIP_OWNER"));
    }

    @Test
    @DisplayName("활동 기록 실패 - 409")
    void createActivityLog_projectNotOngoing() throws Exception {
        // given
        ActivityLogCreateRequest request = new ActivityLogCreateRequest("더미", ACTIVITY_DATE);
        BDDMockito.given(activityLogService.createActivityLog(
                        BDDMockito.eq(10L), BDDMockito.eq(1L), BDDMockito.any(ActivityLogCreateRequest.class)))
                .willThrow(new ProjectNotOngoingException());

        // when
        ResultActions result = mockMvc.perform(post(url, 1L)
                .param("userId", String.valueOf(10L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("PROJECT_NOT_ONGOING"));
    }
}
