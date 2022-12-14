package com.domo.ecommerce.service.admin;

import static com.domo.ecommerce.type.MemberStatus.DELETED;

import com.domo.ecommerce.dto.member.MemberDto;
import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.exception.DuplicateMemberIdException;
import com.domo.ecommerce.exception.LoginFailException;
import com.domo.ecommerce.exception.NotAdminLoginException;
import com.domo.ecommerce.exception.NotFoundMemberException;
import com.domo.ecommerce.repository.MemberRepository;
import com.domo.ecommerce.type.Role;
import com.domo.ecommerce.utils.SHA256Util;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final MemberRepository memberRepository;

    /**
     * 관리자 로그인 조회된 결과가 없다면 null 반환
     *
     * @param id       로그인 아이디
     * @param password 로그인 비밀번호
     * @return
     */
    public void login(String id, String password) {
        String encPassword = SHA256Util.encryptSHA256(password);
        Member member = memberRepository.findByMemberIdAndPassword(id, encPassword)
                .orElseThrow(() -> new LoginFailException("아이디 혹은 비밀번호가 올바르지 않습니다."));

        if (member.getStatus() == DELETED) {
            throw new LoginFailException("삭제 된 회원입니다.");
        }

        if (member.getRole() != Role.ADMIN) {
            throw new NotAdminLoginException("관리자가 아닙니다.");
        }
    }

    public Page<MemberDto> getMemberList(Pageable pageable) {
        return MemberDto.ofPageList(memberRepository.findAll(pageable));
    }

    public MemberDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundMemberException("id {" + id + "} 존재하지 않는 회원입니다."));
        return MemberDto.of(member);
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundMemberException("id {" + id + "} 존재하지 않는 회원입니다."));
        memberRepository.delete(member);
    }
}
