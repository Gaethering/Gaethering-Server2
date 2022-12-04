package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.domain.type.MemberStatus;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if(MemberStatus.INACTIVE == member.getStatus()) {
            throw new RuntimeException("비활성화된 계정입니다.");
        } else if (MemberStatus.DORMANT == member.getStatus()) {
            throw new RuntimeException("휴면 계정입니다.");
        }

        return new User(member.getEmail(), member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())));
    }
}
