package com.gaethering.modulemember.service;

import com.gaethering.modulemember.dto.LoginRequest;
import com.gaethering.modulemember.dto.LoginResponse;
import com.gaethering.modulemember.dto.ReissueTokenRequest;
import com.gaethering.modulemember.dto.ReissueTokenResponse;

public interface MemberService {

    void sendEmailAuthCode(String email);

    void confirmEmailAuthCode(String code);

    LoginResponse login(LoginRequest request);

    ReissueTokenResponse reissue (ReissueTokenRequest request);

}
