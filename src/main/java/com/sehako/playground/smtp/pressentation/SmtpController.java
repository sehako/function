package com.sehako.playground.smtp.pressentation;

import com.sehako.playground.global.response.JSONResponse;
import com.sehako.playground.smtp.application.SmtpService;
import com.sehako.playground.smtp.pressentation.request.EmailSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class SmtpController {
    private final SmtpService smtpService;

    @PostMapping("/send")
    public ResponseEntity<JSONResponse<Object>> sendAuthentication(@RequestBody EmailSendRequest request) {
        smtpService.sendEmail(request.toDto());
        return ResponseEntity.ok(JSONResponse.onSuccess(null));
    }

    @GetMapping("/verify")
    public ResponseEntity<JSONResponse<Object>> emailVerify(
            @RequestParam("token") String token
    ) {
        smtpService.verify(token);
        return ResponseEntity.ok(JSONResponse.onSuccess(null));
    }
}
