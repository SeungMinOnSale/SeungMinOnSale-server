package com.yaho.seungminonsale.user.controller;

import com.yaho.seungminonsale.user.dto.request.LoginRequest;
import com.yaho.seungminonsale.user.dto.request.SignupRequest;
import com.yaho.seungminonsale.user.dto.response.LoginResponse;
import com.yaho.seungminonsale.user.dto.response.SignupResponse;
import com.yaho.seungminonsale.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {
        SignupResponse response = authService.signup(request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/workers/{id}")
                .buildAndExpand(response.getWorkerId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
