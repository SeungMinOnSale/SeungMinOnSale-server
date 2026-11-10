package com.yaho.seungminonsale.user.dto;

import com.yaho.seungminonsale.user.entity.Role;

public record SignupResponse(
        Long userId,
        String email,
        Role role,
        Long workerId
) {
}