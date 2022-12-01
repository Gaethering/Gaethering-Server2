package com.gaethering.modulemember.exception;

public class DuplicatedEmailException extends MemberException {

    public DuplicatedEmailException() {
        super(MemberErrorCode.DUPLICATED_EMAIL);
    }

}
