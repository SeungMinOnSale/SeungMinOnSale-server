package com.yaho.seungminonsale.teammembership.controller;

import com.yaho.seungminonsale.exception.MembershipAccessDeniedException;
import com.yaho.seungminonsale.exception.ProjectNotFoundException;
import com.yaho.seungminonsale.teammembership.domain.enums.MembershipStatus;
import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;
import com.yaho.seungminonsale.teammembership.service.TeamMembershipService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamMembershipController.class)
class TeamMembershipControllerTest {

    private static final String url = "/api/memberships";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamMembershipService teamMembershipService;

    @Test
    @DisplayName("팀원 목록 조회 성공 - 200")
    void getMemberships_success() throws Exception {
        // given
        TeamMembershipResponse response = new TeamMembershipResponse(
                100L, 1L, "더미", 3L, "더미", MembershipStatus.ACTIVE, LocalDateTime.of(2026, 1, 1, 1, 1));
        BDDMockito.given(teamMembershipService.getMemberships(10L, 1L))
                .willReturn(List.of(response));

        // when
        ResultActions result = mockMvc.perform(get(url)
                .param("userId", String.valueOf(10L))
                .param("projectId", String.valueOf(1L)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[0].workerId").value(3))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    @DisplayName("팀원 목록 조회 실패 - 400")
    void getMemberships_missingUserId() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get(url)
                .param("projectId", String.valueOf(1L)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.errors[0].field").value("userId"));
    }

    @Test
    @DisplayName("팀원 목록 조회 실패 - 403")
    void getMemberships_accessDenied() throws Exception {
        // given
        BDDMockito.given(teamMembershipService.getMemberships(10L, 1L))
                .willThrow(new MembershipAccessDeniedException());

        // when
        ResultActions result = mockMvc.perform(get(url)
                .param("userId", String.valueOf(10L))
                .param("projectId", String.valueOf(1L)));

        // then
        result.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("MEMBERSHIP_ACCESS_DENIED"));
    }
}
