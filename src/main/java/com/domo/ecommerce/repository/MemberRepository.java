package com.domo.ecommerce.repository;

import com.domo.ecommerce.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberId(String memberId);
    Optional<Member> findByMemberIdAndPassword(String MemberId, String password);
}
