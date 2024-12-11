package com.sehako.playground.login.infrastructure.provider;

import com.sehako.playground.login.dto.OAuthAccessToken;
import com.sehako.playground.login.dto.UserInfoDto;
import java.util.Collections;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public interface OAuthProvider {
    RestTemplate restTemplate = new RestTemplate();

    boolean is(String providerName);

    UserInfoDto authenticate(String code);

    default ResponseEntity<OAuthAccessToken> requestAccessToken(String... args) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", args[0]);
        body.add("client_id", args[1]);
        body.add("client_secret", args[2]);
        body.add("redirect_uri", args[3]);
        body.add("grant_type", "authorization_code");
        HttpEntity<MultiValueMap<String, String>> accessTokenRequestEntity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                args[4],
                HttpMethod.POST,
                accessTokenRequestEntity,
                OAuthAccessToken.class
        );
    }
}
