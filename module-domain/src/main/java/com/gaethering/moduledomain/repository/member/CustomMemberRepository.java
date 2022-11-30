package com.gaethering.moduledomain.repository.member;

import com.gaethering.moduledomain.domain.member.Member;
import java.util.Optional;

public interface CustomMemberRepository {
    Optional<Member> findByEmail(String email);
}
