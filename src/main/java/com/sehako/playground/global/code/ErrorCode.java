package com.sehako.playground.global.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SERVER_ERROR(50000, INTERNAL_SERVER_ERROR),
    INVALID_REQUEST(40000, BAD_REQUEST),
    INVALID_AUTHORIZATION_CODE(40000, BAD_REQUEST),

    NOT_SUPPORTED_TOKEN_FORMAT(40000, BAD_REQUEST),

    ACCESS_TOKEN_EXPIRED(40100, UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(40200, UNAUTHORIZED),
    TOKEN_NOT_MATCHED(40300, UNAUTHORIZED),
    ;

    private final int code;
    private final HttpStatus httpStatus;
}
