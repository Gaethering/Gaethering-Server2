package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class NotMatchPasswordException extends MemberException {

    public NotMatchPasswordException() {
        super(MemberErrorCode.NOT_MATCH_PASSWORD);
    }
}
