package com.gaethering.modulemember.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.domain.type.Gender;
import com.gaethering.moduledomain.repository.member.MemberProfileRepository;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.dto.SignUpRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberProfileRepository memberProfileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("회원 가입 성공")
    void signUp_Success() {
        //given
        SignUpRequest request = SignUpRequest.builder()
            .email("gaethering@gmail.com")
            .nickname("개더링")
            .password("1234qwer!")
            .passwordCheck("1234qwer!")
            .name("김진호")
            .phone("010-3230-2498")
            .birth(LocalDate.of(2022, 02, 15))
            .gender(Gender.MALE)
            .isEmailAuth(true)
            .build();

        given(memberRepository.existsByEmail(anyString()))
            .willReturn(false);

        given(passwordEncoder.encode(anyString()))
            .willReturn(anyString());

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

        //when
        String nickname = memberService.signUp(request);

        //then
        assertEquals(request.getNickname(), nickname);
        verify(memberRepository, times(1)).save(captor.capture());
    }

}