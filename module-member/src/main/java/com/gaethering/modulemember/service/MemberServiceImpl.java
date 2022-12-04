package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.dto.*;
import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;
import com.gaethering.modulemember.exception.member.DuplicatedEmailException;

import java.util.Objects;
import java.util.UUID;

import com.gaethering.modulemember.exception.member.InvalidTokenException;
import com.gaethering.modulemember.exception.member.TokenIncorrectException;
import com.gaethering.modulemember.exception.member.TokenNotExistException;
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
            throw new InvalidTokenException(MemberErrorCode.INVALID_REFRESH_TOKEN);
        }

        Authentication authentication = jwtProvider.getAuthentication(request.getAccessToken());
        String email = authentication.getName();

        String refreshToken = redisUtil.getData(email);

        if (Objects.isNull(refreshToken)) {
            throw new TokenNotExistException(MemberErrorCode.NOT_EXIST_REFRESH_TOKEN);
        }

        if (!request.getRefreshToken().equals(refreshToken)) {
            throw new TokenIncorrectException(MemberErrorCode.INCORRECT_REFRESH_TOKEN);
        }

        return jwtProvider.reissueAccessToken(email);
    }
    public void logout (LogoutRequest request) {
        String accessToken = request.getAccessToken();

        if(!jwtProvider.validateTokenExpiration(accessToken)) {
            throw new InvalidTokenException(MemberErrorCode.INVALID_ACCESS_TOKEN);
        }

        Authentication authentication = jwtProvider.getAuthentication(request.getAccessToken());
        String email = authentication.getName();

        redisUtil.deleteData(email);

        Long expiration = jwtProvider.getExpiration(accessToken);
        redisUtil.setBlackList(accessToken, "accessToken", expiration);
    }
}
