package com.sehako.playground.login.infrastructure.provider;

import static com.sehako.playground.global.code.ErrorCode.INVALID_AUTHORIZATION_CODE;

import com.sehako.playground.global.exception.CommonException;
import com.sehako.playground.login.dto.GoogleUserInfo;
import com.sehako.playground.login.dto.OAuthAccessToken;
import com.sehako.playground.login.dto.UserInfoDto;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Slf4j
@Component
public class GoogleOAuthProvider implements OAuthProvider {
    private static final String PROVIDER = "google";

    @Value("${oauth2.provider.google.client-id}")
    private String clientId;
    @Value("${oauth2.provider.google.client-secret}")
    private String clientSecret;
    @Value("${oauth2.provider.google.redirect-uri}")
    private String redirectUri;
    @Value("${oauth2.provider.google.token-uri}")
    private String tokenUri;

    @Value("${oauth2.provider.google.info-uri}")
    private String infoUri;

    @Override
    public boolean is(String providerName) {
        return PROVIDER.equals(providerName);
    }

    @Override
    public UserInfoDto authenticate(String code) {
        return requestUserInfo(code);
    }

    private UserInfoDto requestUserInfo(String code) {
        String accessToken = getAccessToken(code);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<MultiValueMap<String, String>> userInfoRequestEntity = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserInfo> userResponse = restTemplate.exchange(
                infoUri,
                HttpMethod.GET,
                userInfoRequestEntity,
                GoogleUserInfo.class
        );

        return Optional.ofNullable(userResponse.getBody())
                .orElseThrow(() -> new CommonException(INVALID_AUTHORIZATION_CODE))
                .toUserInfoDto();
    }
    
    private String getAccessToken(final String code) {
        ResponseEntity<OAuthAccessToken> accessTokenResponse = requestAccessToken(code,
                clientId,
                clientSecret,
                redirectUri,
                tokenUri
        );

        return Optional.ofNullable(accessTokenResponse.getBody())
                .orElseThrow(() -> new CommonException(INVALID_AUTHORIZATION_CODE))
                .accessToken();
    }

}
