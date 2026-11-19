package com.yaho.seungminonsale.activitylog.dto;

import com.yaho.seungminonsale.activitylog.domain.entity.ActivityLog;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ActivityLogResponse(
        Long id,
        Long membershipId,
        String content,
        LocalDate activityDate,
        LocalDateTime createdAt
) {
    public static ActivityLogResponse from(ActivityLog activityLog) {
        return new ActivityLogResponse(
                activityLog.getId(),
                activityLog.getTeamMembership().getId(),
                activityLog.getContent(),
                activityLog.getActivityDate(),
                activityLog.getCreatedAt()
        );
    }
}
