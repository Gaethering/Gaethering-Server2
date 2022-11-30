package com.gaethering.modulemember.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.domain.member.MemberProfile;
import com.gaethering.moduledomain.domain.member.Pet;
import com.gaethering.moduledomain.domain.type.Gender;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.modulemember.dto.OwnProfileResponse;
import com.gaethering.modulemember.exception.errorcode.MemberErrorCode;
import com.gaethering.modulemember.exception.member.MemberNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberProfileServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberProfileServiceImpl memberProfileService;

    private Member member;

    @BeforeEach
    public void setUP() {
        Pet pet1 = Pet.builder()
            .id(1L)
            .name("pet1")
            .isRepresentative(true)
            .build();
        Pet pet2 = Pet.builder()
            .id(2L)
            .name("pet2")
            .isRepresentative(false)
            .build();
        List<Pet> pets = List.of(pet1, pet2);
        MemberProfile memberProfile = MemberProfile.builder()
            .id(1L)
            .gender(Gender.MALE)
            .phoneNumber("010-0000-0000")
            .mannerDegree(36.5f)
            .build();
        member = Member.builder()
            .id(1L)
            .email("member1@test.com")
            .memberProfile(memberProfile)
            .pets(pets)
            .build();
    }

    @Test
    public void getOwnProfileFailure() {
        //given
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        //when
        //then
        MemberNotFoundException exception = assertThrows(
            MemberNotFoundException.class, () -> memberProfileService.getOwnProfile(anyString()));
        assertThat(exception.getErrorCode()).isEqualTo(MemberErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void getOwnProfileSuccess() {
        //given
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(member));

        //when
        OwnProfileResponse profile = memberProfileService.getOwnProfile(anyString());

        //then
        assertMember(profile);
        assertMemberProfile(profile);
        assertPets(profile);
    }

    private void assertMember(OwnProfileResponse profile) {
        assertThat(profile.getEmail()).isEqualTo(member.getEmail());
        assertThat(profile.getNickname()).isEqualTo(member.getNickname());
        assertThat(profile.getPhoneNumber()).isEqualTo(member.getMemberProfile().getPhoneNumber());
    }

    private void assertMemberProfile(OwnProfileResponse profile) {
        assertThat(profile.getGender()).isEqualTo(member.getMemberProfile().getGender());
        assertThat(profile.getMannerDegree()).isEqualTo(
            member.getMemberProfile().getMannerDegree());
    }

    private void assertPets(OwnProfileResponse profile) {
        assertThat(profile.getPetCount()).isEqualTo(member.getPets().size());
        assertThat(profile.getPets().get(0).getId()).isEqualTo(member.getPets().get(0).getId());
        assertThat(profile.getPets().get(1).getId()).isEqualTo(member.getPets().get(1).getId());
    }
}