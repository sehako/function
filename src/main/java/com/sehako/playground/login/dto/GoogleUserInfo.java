package com.sehako.playground.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sehako.playground.login.domain.type.AuthType;

public record GoogleUserInfo(
        @JsonProperty("email")
        String email,
        @JsonProperty("name")
        String name
) {
    public UserInfoDto toUserInfoDto() {
        return UserInfoDto
                .builder()
                .email(email)
                .name(name)
                .authType(AuthType.GOOGLE)
                .build();
    }
}
