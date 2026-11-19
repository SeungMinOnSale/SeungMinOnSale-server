package com.yaho.seungminonsale.application.dto;

import com.yaho.seungminonsale.application.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicationDecisionRequest(
        @NotNull(message = "처리 상태를 제대로 입력하세요.")
        ApplicationStatus status
) {
}
