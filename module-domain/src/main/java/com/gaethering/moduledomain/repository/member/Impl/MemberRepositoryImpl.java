package com.gaethering.moduledomain.repository.member.Impl;

import static com.gaethering.moduledomain.domain.member.QMember.member;
import static com.gaethering.moduledomain.domain.member.QMemberProfile.memberProfile;
import static com.gaethering.moduledomain.domain.member.QPet.pet;

import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.repository.member.CustomMemberRepository;
import com.gaethering.moduledomain.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Optional;

public class MemberRepositoryImpl extends Querydsl4RepositorySupport implements
    CustomMemberRepository {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(
            selectFrom(member)
                .join(member.pets, pet).fetchJoin()
                .join(member.memberProfile, memberProfile).fetchJoin()
                .where(emailEqual(email))
                .fetchOne());
    }

    private static BooleanExpression emailEqual(String email) {
        return member.email.eq(email);
    }
}
