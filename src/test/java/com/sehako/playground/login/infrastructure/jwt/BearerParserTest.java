package com.sehako.playground.login.infrastructure.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BearerParserTest {
    @Test
    void parseTest() {
        String accessToken = BearerParser.parse(
                "Bearer TestAccessToken");

        Assertions.assertThat(accessToken).isEqualTo("TestAccessToken");
    }
}