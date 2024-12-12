package com.sehako.playground.global.exception;

import com.sehako.playground.global.code.ErrorCode;

public class TokenExpiredException extends CommonException {
    public TokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
