package com.domo.ecommerce.controller;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid MemberSignUp.Request request) {
        memberService.signUp(request);
    }

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

}
