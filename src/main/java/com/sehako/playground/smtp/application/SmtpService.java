package com.sehako.playground.smtp.application;

import com.sehako.playground.global.code.ErrorCode;
import com.sehako.playground.global.redis.RedisService;
import com.sehako.playground.login.domain.User;
import com.sehako.playground.login.domain.type.AuthType;
import com.sehako.playground.login.infrastructure.LoginRepository;
import com.sehako.playground.smtp.application.exception.EmailSendException;
import com.sehako.playground.smtp.application.exception.VerifyLinkException;
import com.sehako.playground.smtp.dto.EmailInfoDto;
import com.sehako.playground.smtp.infrastructure.token.TokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpService {
    private final JavaMailSender mailSender;
    private final RedisService redisService;
    private final LoginRepository loginRepository;

    private static final String AUTH_URL = "http://localhost:8080/api/email/verify?token=";
    private static final long EXPIRATION_TIME = 300000L;

    @Async("mailExecutor")
    public void sendEmail(EmailInfoDto info) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String authKey = TokenGenerator.generateEncryptedToken();
            redisService.save(authKey, info.to(), EXPIRATION_TIME);

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(info.to());
            helper.setSubject("auth test");
            helper.setText(AUTH_URL + authKey);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailSendException(ErrorCode.INVALID_REQUEST);
        }
    }

    public void verify(String token) {
        String email = redisService.get(token);
        if (email == null) {
            throw new VerifyLinkException(ErrorCode.INVALID_REQUEST);
        }
        User user = User.builder()
                .email(email)
                .nickname("임시 닉네임")
                .authType(AuthType.EMAIL)
                .build();
        loginRepository.save(user);
        redisService.delete(token);
    }
}
