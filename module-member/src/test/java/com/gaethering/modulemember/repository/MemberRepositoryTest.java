package com.gaethering.modulemember.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.gaethering.moduledomain.config.JpaConfig;
import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.domain.member.MemberProfile;
import com.gaethering.moduledomain.domain.member.Pet;
import com.gaethering.moduledomain.domain.type.Gender;
import com.gaethering.moduledomain.repository.member.MemberProfileRepository;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import com.gaethering.moduledomain.repository.pet.PetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import(JpaConfig.class)
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberProfileRepository memberProfileRepository;
    @Autowired
    private PetRepository petRepository;

    private List<Pet> pets;
    private MemberProfile memberProfile;
    private Member member;

    @BeforeEach
    public void setUP() {
        Pet pet1 = Pet.builder()
            .name("pet1")
            .isRepresentative(true)
            .build();
        Pet pet2 = Pet.builder()
            .name("pet2")
            .isRepresentative(false)
            .build();
        pets = List.of(pet1, pet2);
        memberProfile = MemberProfile.builder()
            .gender(Gender.MALE)
            .phoneNumber("010-0000-0000")
            .mannerDegree(36.5f)
            .build();
        member = Member.builder()
            .email("member1@test.com")
            .memberProfile(memberProfile)
            .pets(new ArrayList<>())
            .build();
        for (Pet pet : pets) {
            member.addPet(pet);
        }
        petRepository.saveAll(pets);
        memberProfileRepository.save(memberProfile);
        memberRepository.save(member);
    }

    @Test
    public void findByEmailFailure() {
        //given
        String email = "wrongEmail";

        //when
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        //then
        assertThat(optionalMember.isPresent()).isFalse();
    }

    @Test
    public void findByEmailSuccess() {
        //given
        String email = member.getEmail();

        //when
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        //then
        assertThat(optionalMember.isPresent()).isTrue();
        assertThat(optionalMember.get().getEmail()).isEqualTo(member.getEmail());
        assertThat(optionalMember.get().getMemberProfile()).isEqualTo(memberProfile);
        for (Pet pet : pets) {
            assertThat(optionalMember.get().getPets().contains(pet)).isTrue();
        }
    }
}
