package com.sehako.playground.fcm.dto;

public record FcmInfoDto(
        String token,
        String title,
        String body
) {
}
