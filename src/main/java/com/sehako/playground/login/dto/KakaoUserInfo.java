package com.sehako.playground.login.dto;

import com.sehako.playground.login.domain.type.AuthType;

public record KakaoUserInfo(
        String name
) {
    public UserInfoDto toUserInfoDto() {
        return UserInfoDto
                .builder()
                .name(name)
                .authType(AuthType.KAKAO)
                .build();
    }
}
