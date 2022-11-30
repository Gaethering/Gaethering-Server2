package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;

public class MemberNotFoundException extends MemberException {

    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
