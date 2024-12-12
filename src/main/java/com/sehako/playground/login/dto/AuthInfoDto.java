package com.sehako.playground.login.dto;

import com.sehako.playground.login.domain.User;

public record AuthInfoDto(
        Long id,
        String email,
        String nickname,
        UserToken userToken
) {
    public static AuthInfoDto convertFrom(User user, UserToken userToken) {
        return new AuthInfoDto(user.getId(), user.getEmail(), user.getNickname(), userToken);
    }
}
