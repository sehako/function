package com.sehako.playground.login.infrastructure.provider;

import com.sehako.playground.login.dto.UserInfoDto;
import org.springframework.web.client.RestTemplate;

public interface OAuthProvider {
    RestTemplate restTemplate = new RestTemplate();

    boolean is(String providerName);

    UserInfoDto authenticate(String code);
}
