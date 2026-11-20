package com.yaho.seungminonsale.project.dto;

import com.yaho.seungminonsale.project.entity.ProjectStatus;

public record ProjectResponse(
        Long id,
        Long ownerId,
        String name,
        String description,
        ProjectStatus status
) {
}