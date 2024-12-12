package com.sehako.playground.login.dto;

import com.sehako.playground.login.domain.type.AuthType;
import lombok.Builder;

@Builder
public record AuthUserInfoDto(
        String email,
        String name,
        AuthType authType
) {
}
