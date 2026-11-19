package com.yaho.seungminonsale.exception;

public class RecruitmentCapacityExceededException extends BusinessException {
    public RecruitmentCapacityExceededException() {
        super(ErrorCode.RECRUITMENT_CAPACITY_EXCEEDED);
    }
}
