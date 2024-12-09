package com.sehako.playground.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String messageCode;
}
