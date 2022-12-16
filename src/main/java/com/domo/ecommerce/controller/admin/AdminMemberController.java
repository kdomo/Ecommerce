package com.domo.ecommerce.controller.admin;

import com.domo.ecommerce.aop.AdminLoginCheck;
import com.domo.ecommerce.dto.member.MemberLogin;
import com.domo.ecommerce.service.admin.AdminMemberService;
import com.domo.ecommerce.utils.SessionUtil;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    /**
     * 관리자 로그인을 진행한다. Login 요청 시 id, password가 Blank시
     * MethodArgumentNotValidException을 발생시켜 404 상태코드를 반환한다.
     *
     * @param request 로그인 요청 정보
     * @param session 사용자의 세션
     * @return
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void adminLogin(
            @RequestBody @Valid MemberLogin.Request request,
            HttpSession session) {
        String id = request.getMemberId();
        String password = request.getPassword();

        adminMemberService.login(id, password);
        SessionUtil.setLoginAdminId(session, id);
    }

    @GetMapping("/logout")
    @AdminLoginCheck
    public void logout(HttpSession session) {
        SessionUtil.logoutAdmin(session);
    }

}
