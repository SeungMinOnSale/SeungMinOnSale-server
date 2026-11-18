package com.yaho.seungminonsale.user.controller;

import com.yaho.seungminonsale.common.exception.BusinessException;
import com.yaho.seungminonsale.common.exception.ErrorCode;
import com.yaho.seungminonsale.user.dto.SignupRequest;
import com.yaho.seungminonsale.user.dto.SignupResponse;
import com.yaho.seungminonsale.user.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    private static final String SIGNUP_BODY = """
            {"email":"student@test.com","password":"password123","name":"김학생","skills":"Java"}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    @DisplayName("회원가입에 성공하면 201과 회원 ID, 학생 프로필 ID를 반환한다")
    void signupSuccess() throws Exception {
        given(authService.signup(any(SignupRequest.class)))
                .willReturn(new SignupResponse(1L, 1L, "student@test.com"));

        MvcResult result = mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SIGNUP_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.workerId").value(1))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("student@test.com");
    }

    @Test
    @DisplayName("이미 가입된 이메일로 회원가입하면 409와 오류 응답을 반환한다")
    void signupDuplicateEmail() throws Exception {
        given(authService.signup(any(SignupRequest.class)))
                .willThrow(new BusinessException(ErrorCode.DUPLICATE_EMAIL));

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SIGNUP_BODY))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("DUPLICATE_EMAIL"));
    }
}
