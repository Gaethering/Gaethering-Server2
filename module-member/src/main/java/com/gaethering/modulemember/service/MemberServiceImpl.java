package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.exception.DuplicatedEmailException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

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

}
