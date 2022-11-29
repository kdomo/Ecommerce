package com.domo.ecommerce.service;

import com.domo.ecommerce.dto.member.MemberDto;
import com.domo.ecommerce.dto.member.MemberSignUp.Request;
import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.exception.DuplicateMemberIdException;
import com.domo.ecommerce.repository.MemberRepository;
import com.domo.ecommerce.type.MemberStatus;
import com.domo.ecommerce.type.Role;
import com.domo.ecommerce.utils.SHA256Util;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(Request request) {
        isDuplicatedMemberId(request.getMemberId());

        memberRepository.save(
                Member.builder()
                        .memberId(request.getMemberId())
                        .password(SHA256Util.encryptSHA256(request.getPassword()))
                        .name(request.getName())
                        .tel(request.getTel())
                        .status(MemberStatus.DEFAULT)
                        .role(Role.MEMBER)
                        .addressCode(request.getAddressCode())
                        .address(request.getAddress())
                        .addressDetail(request.getAddressDetail())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * 아이디 중복체크
     *
     * @param memberId
     * @return true : 중복된 아이디, false : 중복되지 않은 아이디 (생성가능)
     */
    public void isDuplicatedMemberId(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isPresent()) {
            throw new DuplicateMemberIdException("중복된 아이디 입니다.");
        }
    }

    public MemberDto login(String id, String password) {
        String encPassword = SHA256Util.encryptSHA256(password);
        Optional<Member> optionalMember =
                memberRepository.findByMemberIdAndPassword(id, encPassword);
        if (!optionalMember.isPresent()) {
            return null;
        }
        return MemberDto.of(optionalMember.get());
    }
}
