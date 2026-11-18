package com.yaho.seungminonsale.activitylog.dto;

import com.yaho.seungminonsale.activitylog.domain.entity.ActivityLog;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ActivityLogCreateRequest(
        @NotBlank(message = "활동 내용을 입력하세요.")
        @Size(max = ActivityLog.CONTENT_MAX_LENGTH, message = "활동 내용은 255자 이하입니다.")
        String content,

        @NotNull(message = "활동 날짜를 입력하세요.")
        LocalDate activityDate
) {
}
