package com.gaethering.modulemember.exception.errorcode;

import lombok.Getter;

@Getter
public enum MemberErrorCode {
    MEMBER_NOT_FOUND("E001", "존재하지 않는 회원입니다.");

    private final String code;
    private final String message;

    MemberErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
