package com.sehako.playground.login.dto;

import com.sehako.playground.login.domain.type.AuthType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoDto {
    private String email;
    private String name;
    private AuthType authType;

    @Builder
    private UserInfoDto(String email, String name, AuthType authType) {
        this.email = email;
        this.name = name;
        this.authType = authType;
    }
}
