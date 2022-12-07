package com.domo.ecommerce.dto.member;

import javax.validation.constraints.NotBlank;
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

}
