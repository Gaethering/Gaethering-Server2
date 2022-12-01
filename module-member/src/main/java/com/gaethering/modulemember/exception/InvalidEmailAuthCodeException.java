package com.gaethering.modulemember.exception;

public class InvalidEmailAuthCodeException extends MemberException {

    public InvalidEmailAuthCodeException() {
        super(MemberErrorCode.INVALID_EMAIL_AUTH_CODE);
    }

}
