package com.gaethering.modulemember.service;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.domain.type.MemberStatus;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.exception.member.DormantUserException;
import com.gaethering.modulemember.exception.member.InActiveUserException;
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
            throw new InActiveUserException();
        } else if (MemberStatus.DORMANT == member.getStatus()) {
            throw new DormantUserException();
        }

        return new User(member.getEmail(), member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())));
    }
}
