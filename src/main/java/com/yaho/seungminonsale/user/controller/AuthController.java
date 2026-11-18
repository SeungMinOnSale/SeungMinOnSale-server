package com.yaho.seungminonsale.user.controller;

import com.yaho.seungminonsale.common.response.ApiResponse;
import com.yaho.seungminonsale.user.dto.LoginRequest;
import com.yaho.seungminonsale.user.dto.LoginResponse;
import com.yaho.seungminonsale.user.dto.SignupRequest;
import com.yaho.seungminonsale.user.dto.SignupResponse;
import com.yaho.seungminonsale.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("회원가입이 완료되었습니다.", authService.signup(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("로그인에 성공했습니다.", authService.login(request)));
    }
}
