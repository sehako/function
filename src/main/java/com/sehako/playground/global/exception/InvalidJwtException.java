package com.sehako.playground.global.exception;

import com.sehako.playground.global.code.ErrorCode;

public class InvalidJwtException extends CommonException {
    public InvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
