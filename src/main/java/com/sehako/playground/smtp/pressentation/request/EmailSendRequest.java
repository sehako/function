package com.sehako.playground.smtp.pressentation.request;

import com.sehako.playground.smtp.dto.EmailInfoDto;

public record EmailSendRequest(
        String email
) {
    public EmailInfoDto toDto() {
        return new EmailInfoDto(email);
    }
}
