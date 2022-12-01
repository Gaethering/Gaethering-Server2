package com.gaethering.modulemember.exception;

import com.gaethering.modulecore.exception.ErrorResponse;
import javax.mail.SendFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(MemberException e) {

        ErrorResponse response = ErrorResponse.builder()
            .code(e.getMemberErrorCode().getCode())
            .message(e.getMessage())
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ErrorResponse> handleMailSendException(MailSendException e) {

        ErrorResponse response = ErrorResponse.builder()
            .code(MemberErrorCode.FAILED_SEND_EMAIL.getCode())
            .message(MemberErrorCode.FAILED_SEND_EMAIL.getMessage())
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
