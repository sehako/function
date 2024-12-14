package com.sehako.playground.login.application.exception;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;

public class TokenExpiredException extends CommonException {
    public TokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
