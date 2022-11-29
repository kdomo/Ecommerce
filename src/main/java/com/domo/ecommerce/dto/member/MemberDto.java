package com.domo.ecommerce.dto.member;

import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.type.MemberStatus;
import com.domo.ecommerce.type.Role;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 2905837773343086856L;

    private Long id;

    private String memberId;

    private String password;

    private String name;

    private String tel;

    private MemberStatus status;

    private Role role;

    private String addressCode;

    private String address;

    private String addressDetail;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .name(member.getName())
                .tel(member.getTel())
                .status(member.getStatus())
                .role(member.getRole())
                .addressCode(member.getAddressCode())
                .address(member.getAddress())
                .addressDetail(member.getAddressDetail())
                .build();
    }
}
