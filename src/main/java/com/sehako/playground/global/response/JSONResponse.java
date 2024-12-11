package com.sehako.playground.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.code.SuccessCode;
import com.sehako.playground.global.message.MessageUtil;

@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public record JSONResponse<T>(
        @JsonProperty(value = "isSuccess") boolean isSuccess,
        int code,
        String message,
        @JsonInclude(Include.NON_NULL) T result
) {

    public static <T> JSONResponse<T> onSuccess(T data) {
        SuccessCode code = SuccessCode.REQUEST_SUCCESS;
        String message = MessageUtil.getMessage(code.name());
        return new JSONResponse<>(true, code.getCode(), message, data);
    }

    public static <T> JSONResponse<T> of(SuccessCode successCode, T data) {
        String message = MessageUtil.getMessage(successCode.name());
        return new JSONResponse<>(true, successCode.getCode(), message, data);
    }

    public static <T> JSONResponse<T> onFailure(ErrorCode errorCode, T data) {
        String message = MessageUtil.getMessage(errorCode.name());
        return new JSONResponse<>(false, errorCode.getCode(), message, data);
    }
}
