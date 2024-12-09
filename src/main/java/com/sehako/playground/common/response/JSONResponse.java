package com.sehako.playground.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sehako.playground.common.code.ErrorCode;
import com.sehako.playground.common.code.SuccessCode;
import com.sehako.playground.common.message.MessageUtil;
import lombok.Getter;

@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public record JSONResponse<T>(
        @JsonProperty(value = "isSuccess") boolean isSuccess,
        int code,
        String message,
        @JsonInclude(Include.NON_NULL) T result
) {

    public static <T> JSONResponse<T> onSuccess(SuccessCode successCode, T data) {
        String message = MessageUtil.getMessage(successCode.getMessageCode());
        return new JSONResponse<>(true, successCode.getCode(), message, data);
    }

    public static <T> JSONResponse<T> onFailure(ErrorCode errorCode, T data) {
        String message = MessageUtil.getMessage(errorCode.getMessageCode());
        return new JSONResponse<>(false, errorCode.getCode(), message, data);
    }
}
