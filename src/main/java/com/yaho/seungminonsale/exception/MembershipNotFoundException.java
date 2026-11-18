package com.yaho.seungminonsale.exception;

public class MembershipNotFoundException extends BusinessException {
    public MembershipNotFoundException() {
        super(ErrorCode.MEMBERSHIP_NOT_FOUND);
    }
}
