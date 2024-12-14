package com.sehako.playground.login.presentation;


import com.sehako.playground.global.response.JSONResponse;
import com.sehako.playground.login.application.LoginService;
import com.sehako.playground.login.application.response.LoginResponse;
import com.sehako.playground.login.application.response.UserInfoResponse;
import com.sehako.playground.login.dto.AuthInfoDto;
import com.sehako.playground.login.infrastructure.cookie.CookieHandler;
import com.sehako.playground.login.infrastructure.provider.AuthProviderPicker;
import com.sehako.playground.login.infrastructure.provider.OAuthProvider;
import com.sehako.playground.login.presentation.request.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/auth/{provider}")
    public ResponseEntity<JSONResponse<LoginResponse>> login(
            @PathVariable("provider") String providerName,
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        AuthInfoDto authInfo = loginService.login(loginRequest.code(), providerName);
        CookieHandler.setRefreshTokenToHeader(response, authInfo.userToken().refreshToken());
        return ResponseEntity.ok()
                .body(JSONResponse.onSuccess(
                        new LoginResponse(authInfo.nickname(), "Bearer", authInfo.userToken().accessToken())));
    }

    @PostMapping("/auth")
    public ResponseEntity<JSONResponse<LoginResponse>> login(
            @AuthProviderPicker OAuthProvider provider,
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        AuthInfoDto authInfo = loginService.login(loginRequest.code(), provider);
        CookieHandler.setRefreshTokenToHeader(response, authInfo.userToken().refreshToken());
        return ResponseEntity.ok()
                .body(JSONResponse.onSuccess(
                        new LoginResponse(authInfo.nickname(), "Bearer", authInfo.userToken().accessToken())));
    }

    @GetMapping("/info")
    public ResponseEntity<JSONResponse<UserInfoResponse>> userInfo(
            @CookieValue("refresh-token") String refreshToken,
            @RequestHeader(value = "Authorization") String accessToken) {

        return ResponseEntity.ok().body(JSONResponse.onSuccess(loginService.getUserInfo(accessToken, refreshToken)));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Object> logout(
            HttpServletResponse response
    ) {
        CookieHandler.resetRefreshTokenToHeader(response);
        return ResponseEntity.noContent().build();
    }
}
