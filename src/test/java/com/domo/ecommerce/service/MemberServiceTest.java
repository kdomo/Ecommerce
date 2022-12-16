package com.domo.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.exception.DuplicateMemberIdException;
import com.domo.ecommerce.exception.LoginFailException;
import com.domo.ecommerce.repository.MemberRepository;
import com.domo.ecommerce.utils.SHA256Util;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess() {
        //given
        String memberId = "test아이디1";
        String password = "1q2w3e4r!@#$";
        String name = "홍길동";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";

        given(memberRepository.existsByMemberId(anyString()))
                .willReturn(false);
        given(memberRepository.save(any())).willReturn(null);

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

        //when
        memberService.signUp(
                memberId, password, name, tel, addressCode, address, addressDetail
        );

        //then
        verify(memberRepository, times(1)).save(captor.capture());
        verify(memberRepository, times(1)).existsByMemberId(memberId);
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 아이디 존재")
    void signUpFail_DuplicatedMemberId() {
        //given
        String memberId = "test아이디1";
        String password = "1q2w3e4r!@#$";
        String name = "홍길동";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";

        given(memberRepository.existsByMemberId(anyString()))
                .willReturn(true);
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

        //when
        DuplicateMemberIdException duplicateMemberIdException = assertThrows(
                DuplicateMemberIdException.class, () -> memberService.signUp(
                        memberId, password, name, tel, addressCode, address, addressDetail
                ));
        //then
        verify(memberRepository, times(1)).existsByMemberId(memberId);
        verify(memberRepository, times(0)).save(captor.capture());
        assertEquals(duplicateMemberIdException.getMessage(), "중복된 아이디 입니다.");
        assertEquals(
                duplicateMemberIdException.getClass().getSimpleName(),
                "DuplicateMemberIdException"
        );
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        //given
        String memberId = "test아이디1";
        String password = "1q2w3e4r!@#$";
        String name = "홍길동";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";
        Member member = Member.signUp(
                memberId, password, name, tel, addressCode, address, addressDetail
        );

        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.of(member));

        //when
        memberService.login(memberId, password);

        //then
        verify(memberRepository, times(1))
                .findByMemberIdAndPassword(memberId, SHA256Util.encryptSHA256(password));
    }

    @Test
    @DisplayName("로그인 실패 - 계정 정보 없음")
    void loginFailNotAccountInformation() {
        //given
        String memberId = "test아이디1";
        String password = "1q2w3e4r!@#$";
        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.empty());
        //when
        LoginFailException loginFailException = assertThrows(
                LoginFailException.class, () -> memberService.login(memberId, password)
        );

        //then
        verify(memberRepository, times(1))
                .findByMemberIdAndPassword(memberId, SHA256Util.encryptSHA256(password));
        assertEquals(loginFailException.getMessage(), "아이디 혹은 비밀번호가 올바르지 않습니다.");
        assertEquals(loginFailException.getClass().getSimpleName(), "LoginFailException");
    }
}