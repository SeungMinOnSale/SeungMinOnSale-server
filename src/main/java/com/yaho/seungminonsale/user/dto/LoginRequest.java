package com.yaho.seungminonsale.user.dto;

public record LoginRequest(
        String email,
        String password
) {
}