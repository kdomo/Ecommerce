package com.domo.ecommerce.entity;

import com.domo.ecommerce.common.entity.BaseEntity;
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
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull //회원 아이디
    private String memberId;

    @NotNull //회원 패스워드
    private String password;

    @NotNull //회원 이름
    private String name;

    @NotNull //회원 전화번호
    private String tel;

    @NotNull //회원 상태
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @NotNull //회원 권한
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull //우편번호
    private String addressCode;

    @NotNull //주소
    private String address;

    @NotNull //상세주소
    private String addressDetail;
}
