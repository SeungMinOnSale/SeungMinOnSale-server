package com.yaho.seungminonsale.user.dto;

import com.yaho.seungminonsale.user.entity.Role;

public record SignupRequest(
        String email,
        String password,
        Role role,
        String name,
        String phone,
        String skills
) {
}