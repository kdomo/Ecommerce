package com.domo.ecommerce.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberLogin {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotBlank(message = "아이디를 입력해 주세요.")
        private String memberId;

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private LoginStatus loginStatus;

        private MemberDto memberDto;

        public Response(LoginStatus loginStatus) {
            this.loginStatus = loginStatus;
        }
    }

    public enum LoginStatus {
        SUCCESS, FAIL, DELETED
    }

}
