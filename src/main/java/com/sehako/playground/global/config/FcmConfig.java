package com.sehako.playground.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FcmConfig {
    @Value("${fcm.config-path}")
    private String fcmConfigPath;
    @Value("${fcm.project-id}")
    private String projectId;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new ClassPathResource(fcmConfigPath).getInputStream());
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials).build();

        return FirebaseApp.initializeApp(options);
    }
}
