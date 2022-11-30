package com.gaethering.modulemember.service;

public interface MemberService {

    void sendEmailAuthCode(String email);

    String confirmEmailAuthCode(String code, String email);

}
