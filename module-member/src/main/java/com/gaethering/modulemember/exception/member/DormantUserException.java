package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class DormantUserException extends MemberException{

    public DormantUserException() {
        super(MemberErrorCode.CANNOT_LOGIN_DORMANT_USER);
    }
}
