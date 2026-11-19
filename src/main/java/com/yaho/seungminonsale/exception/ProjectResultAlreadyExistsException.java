package com.yaho.seungminonsale.exception;

public class ProjectResultAlreadyExistsException extends BusinessException {
    public ProjectResultAlreadyExistsException() {
        super(ErrorCode.PROJECT_RESULT_ALREADY_EXISTS);
    }
}
