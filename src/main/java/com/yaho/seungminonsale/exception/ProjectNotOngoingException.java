package com.yaho.seungminonsale.exception;

public class ProjectNotOngoingException extends BusinessException {
    public ProjectNotOngoingException() {
        super(ErrorCode.PROJECT_NOT_ONGOING);
    }
}
