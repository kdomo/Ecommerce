package com.domo.ecommerce.entity;

import com.domo.ecommerce.common.entity.BaseEntity;
import com.domo.ecommerce.dto.member.MemberSignUp;
import com.domo.ecommerce.type.MemberStatus;
import com.domo.ecommerce.type.Role;
import com.domo.ecommerce.utils.SHA256Util;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Member extends BaseEntity {
    protected Member() {

    }

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


    public static Member signUp(MemberSignUp.Request request) {
        Member member = new Member();
        member.memberId = request.getMemberId();
        member.password = SHA256Util.encryptSHA256(request.getPassword());
        member.name = request.getName();
        member.tel = request.getTel();
        member.status = MemberStatus.DEFAULT;
        member.role = Role.MEMBER;
        member.addressCode = request.getAddressCode();
        member.address = request.getAddress();
        member.addressDetail = request.getAddressDetail();
        member.setCreatedAt(LocalDateTime.now());
        return member;
    }
}
