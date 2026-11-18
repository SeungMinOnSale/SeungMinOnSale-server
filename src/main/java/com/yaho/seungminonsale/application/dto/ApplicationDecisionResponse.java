package com.yaho.seungminonsale.application.dto;

import com.yaho.seungminonsale.teammembership.domain.enums.MembershipStatus;

import java.time.LocalDateTime;

public record ApplicationDecisionResponse(
        Long id,
        Long projectId,
        String projectName,
        Long workerId,
        String workerName,
        MembershipStatus status,
        LocalDateTime createdAt
) {
}
