package com.yaho.seungminonsale.common.response;

import com.yaho.seungminonsale.common.exception.ErrorCode;

import java.time.LocalDateTime;

public record ErrorResponse(
        boolean success,
        String errorCode,
        String message,
        LocalDateTime timestamp
) {

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(false, errorCode.name(), message, LocalDateTime.now());
    }
}
