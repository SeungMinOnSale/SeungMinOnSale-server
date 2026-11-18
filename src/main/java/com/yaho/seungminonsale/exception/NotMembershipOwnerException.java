package com.yaho.seungminonsale.exception;

public class NotMembershipOwnerException extends BusinessException {
    public NotMembershipOwnerException() {
        super(ErrorCode.NOT_MEMBERSHIP_OWNER);
    }
}
