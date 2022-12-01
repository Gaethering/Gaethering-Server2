package com.gaethering.modulemember.service;

public interface MemberService {

    void sendEmailAuthCode(String email);

    void confirmEmailAuthCode(String code);

}
