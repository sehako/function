package com.sehako.playground.global.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    // 토큰 저장
    public void save(String key, String value, long expirationMinutes) {
        redisTemplate.opsForValue().set(key, value, expirationMinutes, TimeUnit.MILLISECONDS);
    }

    // 토큰 조회
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 토큰 삭제
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}

