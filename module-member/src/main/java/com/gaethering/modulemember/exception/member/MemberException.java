package com.gaethering.modulemember.exception.member;

import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final MemberErrorCode errorCode;

    protected MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.errorCode = memberErrorCode;
    }
}