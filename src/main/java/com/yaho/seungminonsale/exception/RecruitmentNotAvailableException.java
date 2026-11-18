package com.yaho.seungminonsale.exception;

public class RecruitmentNotAvailableException extends BusinessException {
    public RecruitmentNotAvailableException() {
        super(ErrorCode.RECRUITMENT_NOT_AVAILABLE);
    }
}
