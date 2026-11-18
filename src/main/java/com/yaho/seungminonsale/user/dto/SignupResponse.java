package com.yaho.seungminonsale.user.dto;

public record SignupResponse(
        Long userId,
        Long workerId,
        String email
) {
}
