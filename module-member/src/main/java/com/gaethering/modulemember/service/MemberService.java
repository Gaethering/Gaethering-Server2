package com.gaethering.modulemember.service;

import com.gaethering.modulemember.dto.SignUpRequest;

public interface MemberService {

    void sendEmailAuthCode(String email);

    void confirmEmailAuthCode(String code);

    String signUp(SignUpRequest signUpRequest);

}
