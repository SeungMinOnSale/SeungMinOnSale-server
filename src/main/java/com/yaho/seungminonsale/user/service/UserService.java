package com.yaho.seungminonsale.user.service;

import com.yaho.seungminonsale.user.dto.LoginRequest;
import com.yaho.seungminonsale.user.dto.LoginResponse;
import com.yaho.seungminonsale.user.dto.SignupRequest;
import com.yaho.seungminonsale.user.dto.SignupResponse;

public interface UserService {

    SignupResponse signup(SignupRequest request);

    LoginResponse login(LoginRequest request);
}