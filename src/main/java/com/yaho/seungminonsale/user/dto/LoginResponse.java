package com.yaho.seungminonsale.user.dto;

import com.yaho.seungminonsale.user.entity.Role;
import com.yaho.seungminonsale.user.entity.User;

public record LoginResponse(
        Long userId,
        String email,
        Role role
) {

    public static LoginResponse from(User user) {
        return new LoginResponse(user.getId(), user.getEmail(), user.getRole());
    }
}