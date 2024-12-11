package com.sehako.playground.login.application.response;

import com.sehako.playground.login.domain.User;

public record UserResponse(
        Long id,
        String email,
        String nickname
) {
    public static UserResponse convertFrom(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getNickname());
    }
}
