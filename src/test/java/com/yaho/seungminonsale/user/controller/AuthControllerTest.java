package com.yaho.seungminonsale.user.controller;

import com.yaho.seungminonsale.exception.DuplicateEmailException;
import com.yaho.seungminonsale.user.dto.request.SignupRequest;
import com.yaho.seungminonsale.user.dto.response.SignupResponse;
import com.yaho.seungminonsale.user.service.AuthService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    private static final String url = "/api/auth/signup";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @DisplayName("회원가입 성공 - 201 CREATED")
    void signup_success() throws Exception {
        // given
        SignupRequest request = new SignupRequest("student@test.com", "password123", "김학생", null, "Java");
        SignupResponse response = SignupResponse.builder()
                .userId(1L)
                .workerId(1L)
                .email("student@test.com")
                .build();
        BDDMockito.given(authService.signup(BDDMockito.any(SignupRequest.class)))
                .willReturn(response);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.workerId").value(1))
                .andExpect(jsonPath("$.email").value("student@test.com"));
        assertThat(result.andReturn().getResponse().getHeader("Location")).endsWith("/api/workers/1");
    }

    @Test
    @DisplayName("회원가입 실패(이름) - 400 BAD_REQUEST")
    void signup_blankName() throws Exception {
        // given
        SignupRequest request = new SignupRequest("student@test.com", "password123", " ", null, null);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors[0].message").value("이름을 입력해주세요."));
    }

    @Test
    @DisplayName("회원가입 실패(중복 이메일) - 409 CONFLICT")
    void signup_duplicateEmail() throws Exception {
        // given
        SignupRequest request = new SignupRequest("student@test.com", "password123", "김학생", null, null);
        BDDMockito.given(authService.signup(BDDMockito.any(SignupRequest.class)))
                .willThrow(new DuplicateEmailException());

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_EMAIL"));
    }
}
