package com.sehako.playground.smtp.infrastructure;

import com.sehako.playground.smtp.infrastructure.token.TokenGenerator;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TokenGeneratorTest {

    @Test
    void generateToken() {
        String target = "dhtpgkr1999@naver.com:" + UUID.randomUUID();

        String encryptedToken = TokenGenerator.generateEncryptedToken();
        String decryptedToken = TokenGenerator.decryptToken(encryptedToken);
        System.out.println(encryptedToken);
        System.out.println(decryptedToken);
    }

}