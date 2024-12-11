package com.sehako.playground.login.infrastructure.provider;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.exception.CommonException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderFactory {
    private final List<OAuthProvider> providers;

    public OAuthProvider mapping(String providerName) {
        return providers
                .stream()
                .filter(provider -> provider.is(providerName))
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_REQUEST));
    }
}
