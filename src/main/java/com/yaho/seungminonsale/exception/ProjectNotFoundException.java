package com.yaho.seungminonsale.exception;

public class ProjectNotFoundException extends BusinessException {
    public ProjectNotFoundException() {
        super(ErrorCode.PROJECT_NOT_FOUND);
    }
}
