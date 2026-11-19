package com.yaho.seungminonsale.exception;

public class ApplicationNotFoundException extends BusinessException {
    public ApplicationNotFoundException() {
        super(ErrorCode.APPLICATION_NOT_FOUND);
    }
}
