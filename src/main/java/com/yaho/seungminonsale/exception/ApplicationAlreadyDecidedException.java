package com.yaho.seungminonsale.exception;

public class ApplicationAlreadyDecidedException extends BusinessException {
    public ApplicationAlreadyDecidedException() {
        super(ErrorCode.APPLICATION_ALREADY_DECIDED);
    }
}
