package com.sehako.playground.login.infrastructure.jwt;

import static com.sehako.playground.global.code.ErrorCode.ACCESS_TOKEN_EXPIRED;
import static com.sehako.playground.global.code.ErrorCode.NOT_SUPPORTED_TOKEN_FORMAT;
import static com.sehako.playground.global.code.ErrorCode.REFRESH_TOKEN_EXPIRED;
import static com.sehako.playground.global.code.ErrorCode.TOKEN_NOT_MATCHED;

import com.sehako.playground.global.exception.InvalidJwtException;
import com.sehako.playground.global.exception.TokenExpiredException;
import com.sehako.playground.global.exception.TokenTheftException;
import com.sehako.playground.login.dto.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.UnsupportedOrmXsdVersionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private final long accessTokenExpirationTIme;
    private final long refreshTokenExpirationTIme;
    // redis 적용 전 임시 저장소
    private final HttpSession session;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration-time}") long accessTokenExpirationTIme,
            @Value("${jwt.refresh-token-expiration-time}") long refreshTokenExpirationTime,
            HttpSession session
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationTIme = accessTokenExpirationTIme;
        this.refreshTokenExpirationTIme = refreshTokenExpirationTime;
        this.session = session;
    }

    public UserToken generateToken(String subject) {
        String accessToken = createToken(subject, accessTokenExpirationTIme);
        String refreshToken = createToken("REFRESH_TOKEN", refreshTokenExpirationTIme);
        // 이 부분을 레디스로 대체
        session.setAttribute(refreshToken, subject);
        return new UserToken(refreshToken, accessToken);
    }

    public Long getUserId(String accessToken, String refreshToken) {
        validateTokens(accessToken, refreshToken);

        // 리프레시 토큰이 서버에 없는 경우(탈취된 거거나 뭐 암튼...)
        if (session.getAttribute(refreshToken) == null) {
            throw new InvalidJwtException(TOKEN_NOT_MATCHED);
        }
        return parseToken(accessToken).getPayload().get("id", Long.class);
    }

    public String regenerateAccessToken(String refreshToken) {
        validateRefreshToken(refreshToken);
        String userId = String.valueOf(session.getAttribute(refreshToken));
        if (userId == null) {
            throw new TokenTheftException(REFRESH_TOKEN_EXPIRED);
        }

        return createToken(userId, accessTokenExpirationTIme);
    }

    private String createToken(String id, long expirationTime) {
        // 토큰 발행 시간
        Date issuedData = new Date();
        // 토큰 만료 시간
        Date expirationData = new Date(issuedData.getTime() + expirationTime);
        // 주제(사용자 정보), 발급 시간, 만료 시간, 서명
        return Jwts.builder()
                .claim("id", id)
                .issuedAt(issuedData)
                .expiration(expirationData)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    private Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (UnsupportedOrmXsdVersionException | IllegalArgumentException e) {
            throw new InvalidJwtException(NOT_SUPPORTED_TOKEN_FORMAT);
        }
    }

    // 파싱 도중 예외가 발생하면 만료된 토큰으로 취급
    private void validateAccessToken(String accessToken) {
        try {
            parseToken(accessToken);
        } catch (JwtException e) {
            throw new TokenExpiredException(ACCESS_TOKEN_EXPIRED);
        }
    }

    private void validateRefreshToken(String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (JwtException e) {
            throw new TokenExpiredException(REFRESH_TOKEN_EXPIRED);
        }
    }

    private void validateTokens(String accessToken, String refreshToken) {
        validateAccessToken(accessToken);
        validateRefreshToken(refreshToken);
    }
}
