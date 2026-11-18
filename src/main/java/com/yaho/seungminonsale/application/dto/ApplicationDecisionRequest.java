package com.yaho.seungminonsale.application.dto;

import com.yaho.seungminonsale.application.entity.ApplicationStatus;

public record ApplicationDecisionRequest(
    ApplicationStatus status
) {
}
