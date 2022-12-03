package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class InvalidEmailAuthCodeException extends MemberException {

    public InvalidEmailAuthCodeException() {
        super(MemberErrorCode.INVALID_EMAIL_AUTH_CODE);
    }
}
