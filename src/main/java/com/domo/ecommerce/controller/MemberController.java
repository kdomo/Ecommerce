package com.domo.ecommerce.controller;

import com.domo.ecommerce.dto.member.MemberSignUp;
import com.domo.ecommerce.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
}
