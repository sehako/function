package com.sehako.playground.login.domain;

import com.sehako.playground.global.entity.BaseTimeEntity;
import com.sehako.playground.login.domain.type.AuthType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;
    @Column(nullable = false)
    private String nickname;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthType authType;
    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private Boolean deleted = false;

    @Builder
    public User(Long id, String email, String nickname, AuthType authType, Boolean deleted) {
        this.email = email;
        this.nickname = nickname;
        this.authType = authType;
    }
}
