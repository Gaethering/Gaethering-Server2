package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class InvalidTokenException extends MemberException {

    public InvalidTokenException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode);
    }
}
