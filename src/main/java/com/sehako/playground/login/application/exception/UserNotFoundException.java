package com.sehako.playground.login.application.exception;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;

public class UserNotFoundException extends CommonException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
