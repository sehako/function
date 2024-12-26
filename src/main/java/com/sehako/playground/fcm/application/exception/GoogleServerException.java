package com.sehako.playground.fcm.application.exception;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;

public class GoogleServerException extends CommonException {
    public GoogleServerException(ErrorCode message) {
        super(message);
    }
}
