package com.gaethering.moduledomain.repository.member;

import com.gaethering.moduledomain.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, CustomMemberRepository {
}
