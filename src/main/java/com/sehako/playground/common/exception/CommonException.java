package com.sehako.playground.common.exception;

import com.sehako.playground.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
