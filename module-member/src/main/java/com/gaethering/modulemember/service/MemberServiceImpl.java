package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.domain.member.MemberProfile;
import com.gaethering.moduledomain.domain.type.MemberRole;
import com.gaethering.moduledomain.domain.type.MemberStatus;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.dto.SignUpRequest;
import com.gaethering.modulemember.exception.member.DuplicatedEmailException;
import com.gaethering.modulemember.exception.member.NotMatchPasswordException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final MemberRepository memberRepository;

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
    public void signUp(SignUpRequest signUpRequest) {

        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicatedEmailException();
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordCheck())) {
            throw new NotMatchPasswordException();
        }

        memberRepository.save(Member.builder()
            .email(signUpRequest.getEmail())
            .nickname(signUpRequest.getNickname())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .role(MemberRole.ROLE_USER)
            .status(MemberStatus.ACTIVE)
            .isEmailAuth(signUpRequest.isEmailAuth())
            .memberProfile(MemberProfile.builder()
                .phoneNumber(signUpRequest.getPhone())
                .gender(signUpRequest.getGender())
                .build())
            .build());
    }

}
