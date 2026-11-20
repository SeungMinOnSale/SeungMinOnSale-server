package com.yaho.seungminonsale.recruitment.dto;

public record RecruitmentPostCreateRequest(
        String title,
        String position,
        String requiredSkills,
        Integer capacity
) {
}