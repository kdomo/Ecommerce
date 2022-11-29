package com.domo.ecommerce.entity;

import com.domo.ecommerce.common.entity.BaseEntity;
import com.domo.ecommerce.type.MemberStatus;
import com.domo.ecommerce.type.Role;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 아이디
    private String memberId;

    //회원 패스워드
    private String password;

    //회원 이름
    private String name;

    //회원 전화번호
    private String tel;

    //회원 상태
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    //회원 권한
    @Enumerated(EnumType.STRING)
    private Role role;

    //우편번호
    private String addressCode;

    //주소
    private String address;

    //상세주소
    private String addressDetail;

    @Builder
    public Member(Long id, String memberId, String password, String name, String tel, MemberStatus status,
            Role role, String addressCode, String address, String addressDetail,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.status = status;
        this.role = role;
        this.addressCode = addressCode;
        this.address = address;
        this.addressDetail = addressDetail;
        super.setCreatedAt(createdAt);
        super.setUpdatedAt(updatedAt);
    }
}
