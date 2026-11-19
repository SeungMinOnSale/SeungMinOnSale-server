package com.yaho.seungminonsale.recruitmentPost.dto;

public record RecruitmentPostCreateRequest(
        String title,
        String position,
        String requiredSkills,
        Integer capacity
) {
}