package com.domo.ecommerce.dto.member;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberSignUp {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        // @NotBlank(message = "memberId는 필수 값 입니다.")
        @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$",
                message = "아이디는 영어, 숫자 를 포함하여 6~20자리 이내로 입력해 주세요.")
        private String memberId;

        @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",
                message = "비밀번호는 영어, 숫자, 특수문자를 포함해서 8~16자리 이내로 입력해 주세요.")
        private String password;

        @Pattern(regexp = "^[가-힣]{2,4}$", message = "이름을 정확하게 입력해 주세요.")
        private String name;

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
                message = "전화번호는 010-0000-0000 형식으로 입력해 주세요.")
        private String tel;

        @Pattern(regexp = "^\\d{5}$", message = "우편번호는 숫자 5자리를 입력해 주세요.")
        private String addressCode;

        private String address;

        private String addressDetail;
    }

}
