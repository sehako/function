package com.sehako.playground.smtp.application.exception;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;

public class EmailSendException extends CommonException {
    public EmailSendException(ErrorCode code) {
        super(code);
    }
}
