package com.yaho.seungminonsale.user.service;

import com.yaho.seungminonsale.user.dto.request.LoginRequest;
import com.yaho.seungminonsale.user.dto.request.SignupRequest;
import com.yaho.seungminonsale.user.dto.response.LoginResponse;
import com.yaho.seungminonsale.user.dto.response.SignupResponse;

public interface AuthService {
    SignupResponse signup(SignupRequest request);

    LoginResponse login(LoginRequest request);
}
