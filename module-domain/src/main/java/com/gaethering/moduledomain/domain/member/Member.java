package com.gaethering.moduledomain.domain.member;

import com.gaethering.moduledomain.domain.basic.BaseTimeEntity;
import com.gaethering.moduledomain.domain.type.MemberRole;
import com.gaethering.moduledomain.domain.type.MemberStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDateTime accessDate;

    private boolean isEmailAuth;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id")
    private MemberProfile memberProfile;

    @OneToMany(mappedBy = "member")
    private List<Pet> pets = new ArrayList<>();

    public void addPet(Pet pet) {
        pet.setMember(this);
        pets.add(pet);
    }

}
