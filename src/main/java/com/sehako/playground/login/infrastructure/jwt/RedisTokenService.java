package com.sehako.playground.login.infrastructure.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    // 토큰 저장
    public void saveToken(String key, String value, long expirationMinutes) {
        redisTemplate.opsForValue().set(key, value, expirationMinutes, TimeUnit.MILLISECONDS);
    }

    // 토큰 조회
    public String getToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 토큰 삭제
    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
}

