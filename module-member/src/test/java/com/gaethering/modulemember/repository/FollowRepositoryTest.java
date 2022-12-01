package com.gaethering.modulemember.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.gaethering.moduledomain.config.JpaConfig;
import com.gaethering.moduledomain.config.QuerydslConfig;
import com.gaethering.moduledomain.domain.member.Follow;
import com.gaethering.moduledomain.domain.member.Member;
import com.gaethering.moduledomain.repository.follow.FollowRepository;
import com.gaethering.moduledomain.repository.member.MemberRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import({JpaConfig.class, QuerydslConfig.class})
@Transactional
public class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;
    private List<Member> members;

    @BeforeEach
    public void setUP() {
        Member member1 = Member.builder()
            .email("member1@test.com")
            .build();
        Member member2 = Member.builder()
            .email("member2@test.com")
            .build();
        members = List.of(member1, member2);
        memberRepository.saveAll(members);

        Follow follow1 = Follow.builder()
            .follower(member1)
            .followee(member2)
            .build();
        Follow follow2 = Follow.builder()
            .follower(member2)
            .followee(member1)
            .build();
        followRepository.saveAll(List.of(follow1, follow2));

        em.flush();
        em.clear();
    }

    @Test
    public void countByFollowee() {
        //given
        Member member1 = members.get(0);
        Member member2 = members.get(1);

        //when
        Long member1FollowerCount = followRepository.countByFollowee(member1);
        Long member2FollowerCount = followRepository.countByFollowee(member2);

        //then
        assertThat(member1FollowerCount).isEqualTo(1);
        assertThat(member2FollowerCount).isEqualTo(1);
    }

    @Test
    public void countByFollower() {
        //given
        Member member1 = members.get(0);
        Member member2 = members.get(1);

        //when
        Long member1FollowingCount = followRepository.countByFollower(member1);
        Long member2FollowingCount = followRepository.countByFollower(member2);

        //then
        assertThat(member1FollowingCount).isEqualTo(1);
        assertThat(member2FollowingCount).isEqualTo(1);
    }
}
