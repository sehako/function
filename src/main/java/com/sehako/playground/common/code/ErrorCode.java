package com.sehako.playground.common.code;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST(4000, BAD_REQUEST, "invalid.request"),
    SERVER_ERROR(5000, INTERNAL_SERVER_ERROR, "server.error"),;

    private final int code;
    private final HttpStatus httpStatus;
    private final String messageCode;
}
