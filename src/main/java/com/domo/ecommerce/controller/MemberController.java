package com.domo.ecommerce.controller;

import com.domo.ecommerce.aop.MemberLoginCheck;
import com.domo.ecommerce.dto.member.MemberDto;
import com.domo.ecommerce.dto.member.MemberLogin;
import com.domo.ecommerce.dto.member.MemberLogin.LoginStatus;
import com.domo.ecommerce.dto.member.MemberLogin.Response;
import com.domo.ecommerce.dto.member.MemberSignUp;
import com.domo.ecommerce.service.MemberService;
import com.domo.ecommerce.type.MemberStatus;
import com.domo.ecommerce.utils.SessionUtil;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * @Valid 메서드의 파라미터, request에서 지정한 Regex에 통과하지 않는다면
     * MethodArgumentNotValidException을 발생시켜 404 상태코드를 반환한다.
     *
     * 고객이 입력한 정보로 회원가입을 진행한다. ID 중복체크를 진행하여 ID 중복시
     * DuplicateMemberIdException를 발생시켜 409 상태코드를 반환한다.
     *
     * 회원가입에 성공시 201 상태코드를 반환한다.
     *
     * @param request
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid MemberSignUp.Request request) {

        memberService.signUp(
                request.getMemberId(),
                request.getPassword(),
                request.getName(),
                request.getTel(),
                request.getAddressCode(),
                request.getAddress(),
                request.getAddressDetail()
        );
    }

    /**
     * 회원 로그인을 진행한다. Login 요청 시 id, password가 Blank시
     * MethodArgumentNotValidException을 발생시켜 404 상태코드를 반환한다.
     *
     * @param request 로그인 요청 정보
     * @param session 사용자의 세션
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<MemberLogin.Response> login(
            @RequestBody @Valid MemberLogin.Request request,
            HttpSession session) {
        ResponseEntity<MemberLogin.Response> responseEntity = null;
        String id = request.getMemberId();
        String password = request.getPassword();
        MemberLogin.Response response;

        MemberDto memberDto = memberService.login(id, password);
        if (memberDto == null) {
            response = new Response(LoginStatus.FAIL);
            responseEntity = new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (MemberStatus.DEFAULT == memberDto.getStatus()) {
            response = new Response(LoginStatus.SUCCESS, memberDto);
            SessionUtil.setLoginMemberId(session, memberDto.getMemberId());
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            log.error("login ERROR : {}", request);
            throw new RuntimeException("login ERROR");
        }

        return responseEntity;

    }

    @GetMapping("/logout")
    @MemberLoginCheck
    public void logout(HttpSession session) {
        SessionUtil.logoutMember(session);
    }

}
