package com.sehako.playground.fcm.presentation.request;

import com.sehako.playground.fcm.dto.FcmInfoDto;

public record FcmSendRequest(
        String token,
        String title,
        String body
) {
    public FcmInfoDto toDto() {
        return new FcmInfoDto(token, title, body);
    }
}
