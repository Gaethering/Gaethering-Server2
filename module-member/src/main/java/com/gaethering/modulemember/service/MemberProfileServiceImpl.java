package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.repository.follow.FollowRepository;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.dto.OtherProfileResponse;
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
    private final FollowRepository followRepository;

    @Override
    public OwnProfileResponse getOwnProfile(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);
        Long followerCount = followRepository.countByFollowee(member);
        Long followingCount = followRepository.countByFollower(member);
        return OwnProfileResponse.of(member, followerCount, followingCount);
    }

    @Override
    public OtherProfileResponse getOtherProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        Long followerCount = followRepository.countByFollowee(member);
        Long followingCount = followRepository.countByFollower(member);
        return OtherProfileResponse.of(member, followerCount, followingCount);
    }


}
