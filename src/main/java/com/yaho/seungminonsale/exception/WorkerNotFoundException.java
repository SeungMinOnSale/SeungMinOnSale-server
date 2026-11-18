package com.yaho.seungminonsale.exception;

public class WorkerNotFoundException extends BusinessException {
    public WorkerNotFoundException() {
        super(ErrorCode.WORKER_NOT_FOUND);
    }
}
