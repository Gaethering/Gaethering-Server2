package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class TokenNotExistException extends MemberException{

    public TokenNotExistException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode);
    }
}
