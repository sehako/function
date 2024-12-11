package com.sehako.playground.login.application;

import com.sehako.playground.login.application.response.UserResponse;
import com.sehako.playground.login.domain.User;
import com.sehako.playground.login.domain.type.AuthType;
import com.sehako.playground.login.dto.UserInfoDto;
import com.sehako.playground.login.infrastructure.provider.OAuthProvider;
import com.sehako.playground.login.infrastructure.provider.ProviderFactory;
import com.sehako.playground.login.infrastructure.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final ProviderFactory providerFactory;

    public UserResponse login(String code, String providerName) {
        OAuthProvider provider = providerFactory.mapping(providerName);
        UserInfoDto userInfo = provider.authenticate(code);
        User user = getOrCreateUser(userInfo);
        return UserResponse.convertFrom(user);
    }

    private User getOrCreateUser(UserInfoDto userInfoDto) {
        User authUser = null;
        if (userInfoDto.getAuthType() == AuthType.KAKAO) {
            authUser = loginRepository.findByNickname(userInfoDto.getName());
        } else {
            authUser = loginRepository.findByEmail(userInfoDto.getEmail());
        }

        return authUser == null ? createUser(userInfoDto) : authUser;
    }

    private User createUser(UserInfoDto userInfoDto) {
        User persistResult = null;
        if (userInfoDto.getAuthType() == AuthType.KAKAO) {
            persistResult = User.builder()
                    .nickname(userInfoDto.getName())
                    .authType(userInfoDto.getAuthType())
                    .build();


        } else {
            persistResult = User.builder()
                    .email(userInfoDto.getEmail())
                    .nickname(userInfoDto.getName())
                    .authType(userInfoDto.getAuthType())
                    .build();
        }
        loginRepository.save(persistResult);
        return persistResult;
    }
}
