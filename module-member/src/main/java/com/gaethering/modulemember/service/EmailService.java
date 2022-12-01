package com.gaethering.modulemember.service;

public interface EmailService {

    void sendAuthMail(String email, String authCode);

    void confirmAuthCode(String code);

}
