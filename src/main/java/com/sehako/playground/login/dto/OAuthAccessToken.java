package com.sehako.playground.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthAccessToken(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        int expiresIn,
        @JsonProperty("token_in")
        int tokenIn,
        @JsonProperty("scope")
        String scope,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
