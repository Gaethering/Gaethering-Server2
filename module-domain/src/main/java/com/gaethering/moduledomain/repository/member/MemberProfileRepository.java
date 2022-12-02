package com.gaethering.moduledomain.repository.member;

import com.gaethering.moduledomain.domain.member.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

}
