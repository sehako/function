package com.sehako.playground.login.application.response;

public record LoginResponse(
        String name,
        String tokenType,
        String accessToken
) {
}
