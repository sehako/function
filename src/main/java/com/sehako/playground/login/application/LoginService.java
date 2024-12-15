package com.sehako.playground.login.application;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.login.application.exception.UserNotFoundException;
import com.sehako.playground.login.application.response.AccessTokenResponse;
import com.sehako.playground.login.application.response.UserInfoResponse;
import com.sehako.playground.login.domain.User;
import com.sehako.playground.login.domain.type.AuthType;
import com.sehako.playground.login.dto.AuthInfoDto;
import com.sehako.playground.login.dto.AuthUserInfoDto;
import com.sehako.playground.login.dto.UserToken;
import com.sehako.playground.login.infrastructure.LoginRepository;
import com.sehako.playground.login.infrastructure.jwt.JwtUtil;
import com.sehako.playground.login.infrastructure.provider.OAuthProvider;
import com.sehako.playground.login.infrastructure.provider.ProviderFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final ProviderFactory providerFactory;
    private final JwtUtil jwtUtil;

    public AuthInfoDto login(String code, String providerName) {
        OAuthProvider provider = providerFactory.mapping(providerName);
        AuthUserInfoDto authUserInfo = provider.authenticate(code);
        User user = getOrCreateUser(authUserInfo);
        UserToken userToken = jwtUtil.generateToken(String.valueOf(user.getId()));
        return AuthInfoDto.convertFrom(user, userToken);
    }

    public AuthInfoDto login(String code, OAuthProvider provider) {
        AuthUserInfoDto authUserInfo = provider.authenticate(code);
        User user = getOrCreateUser(authUserInfo);
        UserToken userToken = jwtUtil.generateToken(String.valueOf(user.getId()));
        return AuthInfoDto.convertFrom(user, userToken);
    }

    public UserInfoResponse getUserInfo(String accessToken, String refreshToken) {
        Long userId = jwtUtil.getUserId(accessToken, refreshToken);
        Optional<User> loginUserInfo = loginRepository.findById(userId);

        return new UserInfoResponse(
                loginUserInfo.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)).getNickname());
    }

    public AccessTokenResponse reissueAccessToken(String refreshToken) {
        return new AccessTokenResponse(jwtUtil.regenerateAccessToken(refreshToken));
    }

    public void logout(String refreshToken) {
        jwtUtil.cacheOutRefreshToken(refreshToken);
    }

    private User getOrCreateUser(AuthUserInfoDto authUserInfoDto) {
        User authUser = null;
        if (authUserInfoDto.authType() == AuthType.KAKAO) {
            authUser = loginRepository.findByNickname(authUserInfoDto.name());
        } else {
            authUser = loginRepository.findByEmail(authUserInfoDto.name());
        }
        return authUser == null ? createUser(authUserInfoDto) : authUser;
    }

    private User createUser(AuthUserInfoDto authUserInfoDto) {
        User persistResult = null;
        if (authUserInfoDto.authType() == AuthType.KAKAO) {
            persistResult = User.builder()
                    .nickname(authUserInfoDto.name())
                    .authType(authUserInfoDto.authType())
                    .build();


        } else {
            persistResult = User.builder()
                    .email(authUserInfoDto.email())
                    .nickname(authUserInfoDto.name())
                    .authType(authUserInfoDto.authType())
                    .build();
        }
        loginRepository.save(persistResult);
        return persistResult;
    }
}
