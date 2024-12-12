package com.sehako.playground.global.exception;

import com.sehako.playground.global.code.ErrorCode;

public class TokenTheftException extends CommonException {
    public TokenTheftException(ErrorCode errorCode) {
        super(errorCode);
    }
}
