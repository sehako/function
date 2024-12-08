package com.sehako.function.user.domain;

import com.sehako.function.common.entity.BaseTimeEntity;
import com.sehako.function.user.domain.type.AuthType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthType authType;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private Boolean deleted;
}
