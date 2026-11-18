package com.yaho.seungminonsale.teammembership.dto;

import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import com.yaho.seungminonsale.teammembership.domain.enums.MembershipStatus;

import java.time.LocalDateTime;

public record TeamMembershipResponse(
        Long id,
        Long projectId,
        String projectName,
        Long workerId,
        String workerName,
        MembershipStatus status,
        LocalDateTime createdAt
) {
    public static TeamMembershipResponse from(TeamMembership membership) {
        return new TeamMembershipResponse(
                membership.getId(),
                membership.getProject().getId(),
                membership.getProject().getName(),
                membership.getWorker().getId(),
                membership.getWorker().getName(),
                membership.getStatus(),
                membership.getCreatedAt()
        );
    }
}
