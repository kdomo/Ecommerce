package com.domo.ecommerce.service.admin;

import static com.domo.ecommerce.type.MemberStatus.DEFAULT;
import static com.domo.ecommerce.type.MemberStatus.DELETED;
import static com.domo.ecommerce.type.Role.ADMIN;
import static com.domo.ecommerce.type.Role.MEMBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.exception.LoginFailException;
import com.domo.ecommerce.exception.NotAdminLoginException;
import com.domo.ecommerce.repository.MemberRepository;
import com.domo.ecommerce.utils.SHA256Util;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminMemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AdminMemberService adminMemberService;

    @Test
    @DisplayName("관리자 로그인 성공")
    void adminLoginSuccess() {
        //given
        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.of(
                        Member.builder()
                                .id(1L)
                                .status(DEFAULT)
                                .role(ADMIN)
                                .build()
                ));
        //when
        adminMemberService.login("관리자TestId", "관리자TestPassword");

        //then
        verify(memberRepository, times(1))
                .findByMemberIdAndPassword(
                        "관리자TestId",
                        SHA256Util.encryptSHA256("관리자TestPassword")
                );
    }

    @Test
    @DisplayName("관리자 로그인 실패 - 관리자 정보 없음")
    void adminLoginFailNotAccountInformation() {
        //given
        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.empty());
        //when
        LoginFailException loginFailException = assertThrows(
                LoginFailException.class,
                () -> adminMemberService.login("관리자TestId", "관리자TestPassword")
        );

        //then
        assertEquals(loginFailException.getMessage(), "아이디 혹은 비밀번호가 올바르지 않습니다.");
        assertEquals(loginFailException.getClass().getSimpleName(), "LoginFailException");
    }

    @Test
    @DisplayName("관리자 로그인 실패 - 삭제 된 회원")
    void adminLoginFailDeletedAdmin() {
        //given
        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.of(
                        Member.builder()
                                .status(DELETED)
                                .build()
                ));
        //when
        LoginFailException loginFailException = assertThrows(
                LoginFailException.class,
                () -> adminMemberService.login("관리자TestId", "관리자TestPassword")
        );

        //then
        assertEquals(loginFailException.getMessage(), "삭제 된 회원입니다.");
        assertEquals(loginFailException.getClass().getSimpleName(), "LoginFailException");
    }

    @Test
    @DisplayName("관리자 로그인 실패 - 관리자 아님")
    void adminLoginFailNotAdmin() {
        //given
        given(memberRepository.findByMemberIdAndPassword(anyString(), anyString()))
                .willReturn(Optional.of(
                        Member.builder()
                                .role(MEMBER)
                                .build()
                ));
        //when
        NotAdminLoginException notAdminLoginException = assertThrows(NotAdminLoginException.class,
                () -> adminMemberService.login("관리자TestId", "관리자TestPassword"));
        //then
        assertEquals(notAdminLoginException.getMessage(), "관리자가 아닙니다.");
        assertEquals(notAdminLoginException.getClass().getSimpleName(), "NotAdminLoginException");
    }
}