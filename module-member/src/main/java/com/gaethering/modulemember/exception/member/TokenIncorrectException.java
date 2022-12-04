package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class TokenIncorrectException extends MemberException {

    public TokenIncorrectException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode);
    }
}
