package com.sehako.playground.login.application.exception;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;

public class InvalidJwtException extends CommonException {
    public InvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
