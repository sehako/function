package com.sehako.playground.login.presentation.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterRequest(
        @NotNull
        Long id,
        @NotBlank
        String name
) {
}
