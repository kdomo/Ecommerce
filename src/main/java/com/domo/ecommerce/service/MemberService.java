package com.domo.ecommerce.service;

import static com.domo.ecommerce.type.MemberStatus.DEFAULT;
import static com.domo.ecommerce.type.MemberStatus.DELETED;
import static com.domo.ecommerce.type.Role.MEMBER;

import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.exception.DuplicateMemberIdException;
import com.domo.ecommerce.exception.LoginFailException;
import com.domo.ecommerce.repository.MemberRepository;
import com.domo.ecommerce.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 고객 회원가입 메서드로 회원가입 진행 전 중복체크를 한다.
     * 중복체크 시 이상이 없다면 insert를 진행한다.
     * 입력된 비밀번호를 암호화하여 저장한다.
     *
     * @param memberId, password ,name, tel, addressCode, address, addressDetail
     */
    public void signUp(
            String memberId, String password, String name, String tel,
            String addressCode, String address, String addressDetail
    ) {
        isDuplicatedMemberId(memberId);

        memberRepository.save(
                Member.builder()
                        .memberId(memberId)
                        .password(SHA256Util.encryptSHA256(password))
                        .name(name)
                        .tel(tel)
                        .addressCode(addressCode)
                        .address(address)
                        .addressDetail(addressDetail)
                        .status(DEFAULT)
                        .role(MEMBER)
                        .build()
        );
    }

    /**
     * 아이디 중복체크
     * 중복된 아이디라면 DuplicateMemberIdException를 발생시켜
     * 409 상태코드를 반환한다.
     *
     * @param memberId
     * @return true : 중복된 아이디, false : 중복되지 않은 아이디 (생성가능)
     */
    public void isDuplicatedMemberId(String memberId) {
        if (memberRepository.existsByMemberId(memberId)) {
            throw new DuplicateMemberIdException("중복된 아이디 입니다.");
        }
    }


    /**
     * 회원 로그인 조회된 결과가 없다면 null 반환
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
    }
}
