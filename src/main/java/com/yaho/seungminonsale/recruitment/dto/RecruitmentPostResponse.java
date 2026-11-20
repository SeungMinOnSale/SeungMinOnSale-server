package com.yaho.seungminonsale.recruitment.dto;

import com.yaho.seungminonsale.recruitment.entity.RecruitmentStatus;

public record RecruitmentPostResponse(
        Long id,
        Long projectId,
        String title,
        String position,
        String requiredSkills,
        Integer capacity,
        RecruitmentStatus status
) {
}