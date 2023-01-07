package com.domo.ecommerce.dto.member;

import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.type.MemberStatus;
import com.domo.ecommerce.type.Role;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 2905837773343086856L;

    private Long id;

    private String memberId;

    private String name;

    private String tel;

    private MemberStatus status;

    private Role role;

    private String addressCode;

    private String address;

    private String addressDetail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .tel(member.getTel())
                .status(member.getStatus())
                .role(member.getRole())
                .addressCode(member.getAddressCode())
                .address(member.getAddress())
                .addressDetail(member.getAddressDetail())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static Page<MemberDto> ofPageList(Page<Member> members) {
        return members.map(m -> MemberDto.builder()
                .id(m.getId())
                .memberId(m.getMemberId())
                .name(m.getName())
                .tel(m.getTel())
                .status(m.getStatus())
                .role(m.getRole())
                .addressCode(m.getAddressCode())
                .address(m.getAddress())
                .addressDetail(m.getAddressDetail())
                .createdAt(m.getCreatedAt())
                .updatedAt(m.getUpdatedAt())
                .build());
    }
}
