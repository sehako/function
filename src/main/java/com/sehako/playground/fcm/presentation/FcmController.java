package com.sehako.playground.fcm.presentation;

import com.sehako.playground.fcm.application.FcmService;
import com.sehako.playground.fcm.presentation.request.FcmSendRequest;
import com.sehako.playground.global.code.SuccessCode;
import com.sehako.playground.global.response.JSONResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FcmController {
    private final FcmService fcmService;

    @PostMapping("/send")
    public ResponseEntity<JSONResponse<Object>> pushMessage(@RequestBody FcmSendRequest request) {
        fcmService.sendMessage(request.toDto());
        return ResponseEntity.ok(JSONResponse.of(SuccessCode.REQUEST_SUCCESS, null));
    }
}
