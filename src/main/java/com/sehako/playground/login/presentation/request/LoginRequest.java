package com.sehako.playground.login.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String code
) {
}
