package com.sehako.playground.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    REQUEST_SUCCESS(2000, HttpStatus.OK);

    private final int code;
    private final HttpStatus httpStatus;
}
