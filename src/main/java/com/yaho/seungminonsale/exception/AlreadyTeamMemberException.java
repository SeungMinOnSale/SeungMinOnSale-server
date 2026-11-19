package com.yaho.seungminonsale.exception;

public class AlreadyTeamMemberException extends BusinessException {
    public AlreadyTeamMemberException() {
        super(ErrorCode.ALREADY_TEAM_MEMBER);
    }
}
