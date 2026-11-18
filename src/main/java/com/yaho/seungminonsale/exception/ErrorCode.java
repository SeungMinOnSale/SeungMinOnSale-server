package com.yaho.seungminonsale.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."),
    INACTIVE_USER(HttpStatus.FORBIDDEN, "비활성화된 계정입니다."),
    WORKER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 학생을 찾을 수 없습니다."),

    INVALID_DECISION_STATUS(HttpStatus.BAD_REQUEST, "지원 처리 상태는 ACCEPTED 또는 REJECTED만 가능합니다."),
    NOT_PROJECT_OWNER(HttpStatus.FORBIDDEN, "프로젝트 개설자만 처리할 수 있습니다."),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 지원 내역을 찾을 수 없습니다."),
    APPLICATION_ALREADY_DECIDED(HttpStatus.CONFLICT, "이미 처리된 지원입니다."),
    RECRUITMENT_NOT_AVAILABLE(HttpStatus.CONFLICT, "모집이 마감되었거나 종료된 프로젝트입니다."),
    RECRUITMENT_CAPACITY_EXCEEDED(HttpStatus.CONFLICT, "모집 정원이 가득 찼습니다."),
    ALREADY_TEAM_MEMBER(HttpStatus.CONFLICT, "이미 해당 프로젝트의 팀원입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 프로젝트를 찾을 수 없습니다."),
    MEMBERSHIP_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 프로젝트의 팀원 목록을 조회할 권한이 없습니다."),

    MEMBERSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 팀원 정보를 찾을 수 없습니다."),
    NOT_MEMBERSHIP_OWNER(HttpStatus.FORBIDDEN, "본인의 팀원 정보에만 활동을 기록할 수 있습니다."),
    PROJECT_NOT_ONGOING(HttpStatus.CONFLICT, "진행 중인 프로젝트가 아닙니다."),

    PROJECT_RESULT_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 결과가 등록된 프로젝트입니다.");

    ErrorCode(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    private final HttpStatus status;
    private final String message;
}
