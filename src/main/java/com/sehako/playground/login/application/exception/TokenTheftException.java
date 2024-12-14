package com.sehako.playground.login.application.exception;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;

public class TokenTheftException extends CommonException {
    public TokenTheftException(ErrorCode errorCode) {
        super(errorCode);
    }
}
