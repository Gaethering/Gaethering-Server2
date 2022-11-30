package com.gaethering.modulemember.dto;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.domain.type.Gender;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OwnProfileResponse {

    private String email;
    private String nickname;
    private String phoneNumber;
    private Gender gender;
    private float mannerDegree;
    private int petCount;
    private List<ProfilePetResponse> pets;

    public static OwnProfileResponse of(Member member) {
        List<ProfilePetResponse> pets = member.getPets().stream().map(ProfilePetResponse::of)
            .collect(Collectors.toList());
        return OwnProfileResponse.builder()
            .email(member.getEmail())
            .nickname(member.getNickname())
            .phoneNumber(member.getMemberProfile().getPhoneNumber())
            .gender(member.getMemberProfile().getGender())
            .mannerDegree(member.getMemberProfile().getMannerDegree())
            .petCount(member.getPets().size())
            .pets(pets)
            .build();
    }
}
