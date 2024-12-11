package com.sehako.playground.login.presentation;


import com.sehako.playground.global.response.JSONResponse;
import com.sehako.playground.login.application.LoginService;
import com.sehako.playground.login.application.response.UserResponse;
import com.sehako.playground.login.infrastructure.provider.AuthProviderPicker;
import com.sehako.playground.login.infrastructure.provider.OAuthProvider;
import com.sehako.playground.login.presentation.request.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/auth/{provider}")
    public ResponseEntity<JSONResponse<UserResponse>> login(
            @PathVariable("provider") String providerName,
            @RequestBody @Valid LoginRequest loginRequest
    ) {

        return ResponseEntity.ok()
                .body(JSONResponse.onSuccess(loginService.login(loginRequest.code(), providerName)));
    }

    @PostMapping("/auth")
    public ResponseEntity<JSONResponse<UserResponse>> login(
            @AuthProviderPicker OAuthProvider provider,
            @RequestBody @Valid LoginRequest loginRequest
    ) {

        return ResponseEntity.ok()
                .body(JSONResponse.onSuccess(loginService.login(loginRequest.code(), provider)));
    }
}
