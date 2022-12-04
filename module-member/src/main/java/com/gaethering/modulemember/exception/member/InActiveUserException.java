package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class InActiveUserException extends MemberException {

    public InActiveUserException() {
        super(MemberErrorCode.CANNOT_LOGIN_INACTIVE_USER);
    }
}
