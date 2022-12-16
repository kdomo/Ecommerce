package com.domo.ecommerce.repository;

import com.domo.ecommerce.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberId(String memberId);
    Optional<Member> findByMemberIdAndPassword(String MemberId, String password);
    Page<Member> findAll(Pageable pageable);
}
