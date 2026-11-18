package com.yaho.seungminonsale.exception;

public class NotProjectOwnerException extends BusinessException {
    public NotProjectOwnerException() {
        super(ErrorCode.NOT_PROJECT_OWNER);
    }
}
