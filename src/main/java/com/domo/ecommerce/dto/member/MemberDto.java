package com.domo.ecommerce.dto.member;

import com.domo.ecommerce.entity.Member;
import com.domo.ecommerce.type.Role;
import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class MemberDto implements Serializable {
    private static final long serialVersionUID = 2905837773343086856L;

    public enum Status {
        DEFAULT, DELETED
    }

    private Long id;

    private String password;

    private String name;

    private String tel;

    private Member.Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String addressCode;

    private String address;

    private String addressDetail;
}
