package com.sehako.playground.common.exception;

import static org.springframework.http.HttpStatus.*;

import com.sehako.playground.common.code.ErrorCode;
import com.sehako.playground.common.message.MessageUtil;
import com.sehako.playground.common.response.JSONResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
    // Valid 실패 시 발생하는 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JSONResponse<Object>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(fieldError -> MessageUtil
                        .getMessage(fieldError.getCode(),
                                new Object[] { fieldError.getField() }
                        )
                )
                .collect(Collectors.toList());

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(JSONResponse.onFailure(ErrorCode.INVALID_REQUEST, errorMessages));
    }

    // @PathVariable 잘못 입력 또는 요청 메시지 바디에 아무 값도 전달되지 않았을 때
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<JSONResponse<Object>> handlerMethodArgumentTypeMismatchException(final Exception e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(JSONResponse.onFailure(ErrorCode.INVALID_REQUEST, null));
    }

    // 그 외 CommonException 상속받은 모든 예외를 이 메소드에서 처리
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<JSONResponse<Object>> handlerCommonException(final CommonException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(JSONResponse.onFailure(e.getErrorCode(), null));
    }

    // 서버 내부 오류 (SQL 연결 오류 등) 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONResponse<Object>> handlerException(final Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(JSONResponse.onFailure(ErrorCode.SERVER_ERROR, null));
    }
}
