package com.domo.ecommerce.entity;

import com.domo.ecommerce.common.entity.BaseEntity;
import com.domo.ecommerce.type.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member extends BaseEntity {
    public enum Status {
        DEFAULT, DELETED
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
    private Status status;

    //회원 권한
    @Enumerated(EnumType.STRING)
    private Role role;

    //우편번호
    private String addressCode;

    //주소
    private String address;

    //상세주소
    private String addressDetail;
}
