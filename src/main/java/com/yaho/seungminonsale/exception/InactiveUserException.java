package com.yaho.seungminonsale.exception;

public class InactiveUserException extends BusinessException {
    public InactiveUserException() {
        super(ErrorCode.INACTIVE_USER);
    }
}
