package com.gaethering.modulemember.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    INVALID_EMAIL_AUTH_CODE("E901", "이메일 인증 코드가 유효하지 않습니다."),
    DUPLICATED_EMAIL("E902", "중복된 이메일입니다.");

    private final String code;
    private final String message;

}
