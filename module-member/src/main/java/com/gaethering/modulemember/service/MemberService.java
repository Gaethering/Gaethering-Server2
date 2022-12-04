package com.gaethering.modulemember.service;

import com.gaethering.modulemember.dto.*;

public interface MemberService {

    void sendEmailAuthCode(String email);

    void confirmEmailAuthCode(String code);

    LoginResponse login(LoginRequest request);

    ReissueTokenResponse reissue (ReissueTokenRequest request);

    void logout (LogoutRequest request);
}
