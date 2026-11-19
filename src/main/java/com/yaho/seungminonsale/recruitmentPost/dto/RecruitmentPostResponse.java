package com.yaho.seungminonsale.recruitmentPost.dto;

import com.yaho.seungminonsale.recruitmentPost.entity.RecruitmentStatus;

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