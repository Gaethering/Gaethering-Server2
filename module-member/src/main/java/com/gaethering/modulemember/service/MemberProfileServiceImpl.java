package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.dto.OwnProfileResponse;
import com.gaethering.modulemember.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberRepository memberRepository;

    @Override
    public OwnProfileResponse getOwnProfile(String email) {
        return OwnProfileResponse.of(
            memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new));
    }
}
