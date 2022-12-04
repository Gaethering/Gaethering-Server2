package com.gaethering.modulemember.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    MEMBER_NOT_FOUND("E001", "존재하지 않는 회원입니다."),
    INVALID_EMAIL_AUTH_CODE("E901", "이메일 인증 코드가 유효하지 않습니다."),
    DUPLICATED_EMAIL("E902", "중복된 이메일입니다."),
    FAILED_SEND_EMAIL("E903", "이메일 전송에 실패하였습니다."),
    CANNOT_LOGIN_INACTIVE_USER("E101", "비활성화된 계정입니다."),
    CANNOT_LOGIN_DORMANT_USER("E102", "휴면 계정입니다."),
    INVALID_REFRESH_TOKEN("E103",
            "refresh token이 유효하지 않아 access token 재발급이 불가능합니다."),
    INCORRECT_REFRESH_TOKEN("E104",
            "refresh token이 일치하지 않아 access token 재발급이 불가능합니다."),
    NOT_EXIST_REFRESH_TOKEN("E105",
            "refresh token이 존재하지 않아 access token 재발급이 불가능합니다."),
    INVALID_ACCESS_TOKEN("E103",
            "access token이 유효하지 않습니다.");

    private final String code;
    private final String message;

}
