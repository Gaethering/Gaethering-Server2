package com.gaethering.moduledomain.repository.follow;

import com.gaethering.moduledomain.domain.Follow;
import com.gaethering.moduledomain.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Long countByFollowee(Member member);

    Long countByFollower(Member member);
}
