package com.yaho.seungminonsale.exception;

public class InvalidDecisionStatusException extends BusinessException {
    public InvalidDecisionStatusException() {
        super(ErrorCode.INVALID_DECISION_STATUS);
    }
}
