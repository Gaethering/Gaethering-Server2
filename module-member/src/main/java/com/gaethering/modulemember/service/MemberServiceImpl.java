package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.exception.InvalidEmailAuthCodeException;
import com.gaethering.modulemember.util.RedisUtil;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final RedisUtil redisUtil;
    private final JavaMailSender javaMailSender;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void sendEmailAuthCode(String email) {

        String authCode = UUID.randomUUID().toString();

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            makeAuthEmail(email, authCode, message);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redisUtil.setDataExpire(authCode, email, 60 * 5L);
    }

    @Override
    @Transactional
    public void confirmEmailAuthCode(String code) {

        String authEmail = redisUtil.getData(code);

        if (ObjectUtils.isEmpty(authEmail)) {
            throw new InvalidEmailAuthCodeException();
        }
    }

    private static void makeAuthEmail(String email, String authCode, MimeMessage message)
        throws MessagingException {

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf-8");

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("인증코드");
        mimeMessageHelper.setText("회원가입 인증코드: " + authCode + " 입니다.");
    }

}
