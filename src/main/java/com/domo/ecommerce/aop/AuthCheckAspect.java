package com.domo.ecommerce.aop;

import com.domo.ecommerce.exception.NotLoginException;
import com.domo.ecommerce.utils.SessionUtil;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class AuthCheckAspect {

    @Before("@annotation(com.domo.ecommerce.aop.MemberLoginCheck)")
    public void memberLoginCheck(JoinPoint jp) {
        log.debug("AOP - Member Login Check Started");

        HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String memberId = SessionUtil.getLoginMemberId(session);

        if (ObjectUtils.isEmpty(memberId)) {
            log.debug("AOP - Member Login Check Fail");
            throw new NotLoginException("로그인을 해주세요.");
        }
    }


}
