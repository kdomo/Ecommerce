package com.domo.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.domo.ecommerce.dto.member.MemberDto;
import com.domo.ecommerce.dto.member.MemberSignUp;
import com.domo.ecommerce.dto.member.MemberSignUp.Request;
import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.exception.DuplicateMemberIdException;
import com.domo.ecommerce.exception.LoginFailException;
import com.domo.ecommerce.repository.MemberRepository;
import com.domo.ecommerce.type.MemberStatus;
import com.domo.ecommerce.type.Role;
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
    void siguUpSuccess() {
        //given
        MemberSignUp.Request request = new Request();
        request.setMemberId("test아이디1");
        request.setPassword("1q2w3e4r!@#$");
        request.setName("홍길동");
        request.setTel("010-1111-1111");
        request.setAddressCode("11111");
        request.setAddress("경기도 화성시 *** ****");
        request.setAddressDetail("1층");

        given(memberRepository.save(any()))
                .willReturn(
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
                                .build()
                );
        given(memberRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

        //when
        memberService.signUp(request);

        //then
        verify(memberRepository, times(1)).save(captor.capture());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 아이디 존재")
    void siguUpFail() {
        //given
        MemberSignUp.Request request = new Request();
        request.setMemberId("test아이디1");
        request.setPassword("1q2w3e4r!@#$");
        request.setName("홍길동");
        request.setTel("010-1111-1111");
        request.setAddressCode("11111");
        request.setAddress("경기도 화성시 *** ****");
        request.setAddressDetail("1층");

        given(memberRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(
                        Member.builder()
                                .build()
                ));
        //when
        DuplicateMemberIdException duplicateMemberIdException = assertThrows(
                DuplicateMemberIdException.class, ()-> memberService.signUp(request));
        //then
        assertEquals("중복된 아이디 입니다.", duplicateMemberIdException.getMessage());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        //given
        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.of(
                        Member.builder()
                                .memberId("test아이디1")
                                .password(SHA256Util.encryptSHA256("1q2w3e4r!@#$"))
                                .build()
                ));

        //when
        MemberDto memberDto = memberService.login("test아이디1", "1q2w3e4r!@#$");
        //then
        assertEquals(memberDto.getMemberId(), "test아이디1");
        assertEquals(memberDto.getPassword(), SHA256Util.encryptSHA256("1q2w3e4r!@#$"));
    }

    @Test
    @DisplayName("로그인 실패 - 회원정보 없음")
    void loginFail() {
        //given
        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.empty());
        //when
        LoginFailException loginFailException = assertThrows(LoginFailException.class,
                () -> memberService.login("testId", "testPassword"));
        //then
        assertEquals("로그인에 실패하였습니다.", loginFailException.getMessage());

    }
}
