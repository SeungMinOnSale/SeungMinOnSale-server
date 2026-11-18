package com.yaho.seungminonsale.exception;

public class MembershipAccessDeniedException extends BusinessException {
    public MembershipAccessDeniedException() {
        super(ErrorCode.MEMBERSHIP_ACCESS_DENIED);
    }
}
