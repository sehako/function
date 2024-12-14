package com.sehako.playground.login.infrastructure.jwt;

import com.sehako.playground.login.dto.UserToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtUtilTest {
    @Autowired
    JwtUtil jwtUtil;

    @Test
    void tokenTest() {
        UserToken userToken = jwtUtil.generateToken("1");

        String accessToken = userToken.accessToken();
        String refreshToken = userToken.refreshToken();

        Long id = jwtUtil.getUserId(accessToken, refreshToken);

        Assertions.assertThat(id).isEqualTo(1L);
    }
}