package com.domo.ecommerce.entity;

import com.domo.ecommerce.type.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {
    public enum Status {
        DEFAULT, DELETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String name;

    private String tel;

    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String addressCode;

    private String address;

    private String addressDetail;
}
