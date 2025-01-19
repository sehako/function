package com.sehako.playground.smtp.infrastructure.token;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TokenGenerator {
    // Key for AES encryption (256-bit key)
    private static final String SECRET_KEY = "0123456789abcdef0123456789abcdef"; // Replace with a secure key
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;

    // Generate Encrypted Token
    public static String generateEncryptedToken() {
        // Generate a random Initialization Vector (IV)
        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        try {
            // Encrypt data
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] encryptedData = cipher.doFinal(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

            // Combine IV and encrypted data, then encode in Base64
            byte[] combined = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(combined);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
