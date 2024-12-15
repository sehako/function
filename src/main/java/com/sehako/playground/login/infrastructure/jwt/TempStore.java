package com.sehako.playground.login.infrastructure.jwt;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TempStore {
    private static final Map<String, Object> userStore = new HashMap<>();

    public void store(String token, Object userId) {
        log.info("저장소에 값 저장됨");
        userStore.put(token, userId);
    }

    public Object get(String token) {
        log.info("저장소 접근됨 = {}", token);
        return userStore.get(token);
    }

    public void remove(String token) {
        log.info("토큰 삭제됨 = {}", token);
        userStore.remove(token);
    }
}
