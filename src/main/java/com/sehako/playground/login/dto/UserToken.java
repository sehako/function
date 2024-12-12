package com.sehako.playground.login.dto;

public record UserToken(
        String refreshToken,
        String accessToken
) {
}
