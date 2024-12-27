package com.sehako.playground.smtp.application.exception;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;

public class VerifyLinkException extends CommonException {
    public VerifyLinkException(ErrorCode errorCode) {
        super(errorCode);
    }
}
