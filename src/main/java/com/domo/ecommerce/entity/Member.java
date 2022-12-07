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


    public static Member signUp(String memberId, String password, String name, String tel,
            String addressCode, String address, String addressDetail) {
        Member member = new Member();
        member.memberId = memberId;
        member.password = SHA256Util.encryptSHA256(password);
        member.name = name;
        member.tel = tel;
        member.status = MemberStatus.DEFAULT;
        member.role = Role.MEMBER;
        member.addressCode = addressCode;
        member.address = address;
        member.addressDetail = addressDetail;
        member.setCreatedAt(LocalDateTime.now());
        member.setIsDeleted(false);
        return member;
    }
}
