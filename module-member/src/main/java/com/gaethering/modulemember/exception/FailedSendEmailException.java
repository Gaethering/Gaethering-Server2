package com.gaethering.modulemember.exception;

public class FailedSendEmailException extends MemberException {

    public FailedSendEmailException() {
        super(MemberErrorCode.FAILED_SEND_EMAIL);
    }

}
