package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.dto.LoginRequest;
import com.gaethering.modulemember.dto.LoginResponse;
import com.gaethering.modulemember.dto.ReissueTokenRequest;
import com.gaethering.modulemember.dto.ReissueTokenResponse;
import com.gaethering.modulemember.exception.member.DuplicatedEmailException;

import java.util.Objects;
import java.util.UUID;

import com.gaethering.modulemember.jwt.JwtProvider;
import com.gaethering.modulemember.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final EmailService emailService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public void sendEmailAuthCode(String email) {

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }

        String authCode = UUID.randomUUID().toString();

        emailService.sendAuthMail(email, authCode);

    }

    @Override
    @Transactional
    public void confirmEmailAuthCode(String code) {

        emailService.confirmAuthCode(code);

    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword());

            Authentication authentication
                    = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            return jwtProvider.createTokensByLogin(authentication);

        } catch (BadCredentialsException e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public ReissueTokenResponse reissue(ReissueTokenRequest request) {

        if (!jwtProvider.validateTokenExpiration(request.getRefreshToken())) {
            throw new RuntimeException("refresh token 정보가 유효하지 않습니다.");
        }

        Authentication authentication = jwtProvider.getAuthentication(request.getAccessToken());
        String email = authentication.getName();

        String refreshToken = redisUtil.getData(email);

        if (Objects.isNull(refreshToken)) {
            throw new RuntimeException("refresh token 정보가 존재하지 않습니다.");
        }

        if (!request.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("refresh token 정보가 일치하지 않습니다.");
        }

        return jwtProvider.reissueAccessToken(email);
    }

}
