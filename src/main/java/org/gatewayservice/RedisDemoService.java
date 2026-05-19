package org.gatewayservice;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisDemoService {

    private static final String USERNAME_KEY = "demo:username";
    private static final String CODE_KEY = "demo:verification-code";
    private static final String COUNTER_KEY = "demo:click-counter";
    private static final String COURIER_KEY = "demo:courier:1";

    private final StringRedisTemplate redisTemplate;

    public RedisDemoService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveUsername(String username) {
        redisTemplate.opsForValue().set(USERNAME_KEY, username);
    }

    public String getUsername() {
        return redisTemplate.opsForValue().get(USERNAME_KEY);
    }

    public void deleteUsername() {
        redisTemplate.delete(USERNAME_KEY);
    }

    public void saveCodeWithTtl(String code, long seconds) {
        redisTemplate.opsForValue().set(CODE_KEY, code, Duration.ofSeconds(seconds));
    }

    public String getCode() {
        return redisTemplate.opsForValue().get(CODE_KEY);
    }

    public Long getCodeTtl() {
        return redisTemplate.getExpire(CODE_KEY, TimeUnit.SECONDS);
    }

    public Long incrementCounter() {
        return redisTemplate.opsForValue().increment(COUNTER_KEY);
    }

    public void resetCounter() {
        redisTemplate.delete(COUNTER_KEY);
    }

    public String getCounter() {
        String value = redisTemplate.opsForValue().get(COUNTER_KEY);
        return value == null ? "0" : value;
    }

    public void updateCourierStatus(String status) {
        redisTemplate.opsForHash().put(COURIER_KEY, "name", "Ali Courier");
        redisTemplate.opsForHash().put(COURIER_KEY, "status", status);
        redisTemplate.opsForHash().put(COURIER_KEY, "lat", "41.3111");
        redisTemplate.opsForHash().put(COURIER_KEY, "lng", "69.2797");
    }

    public Map<String, Object> getCourierInfo() {
        Map<Object, Object> redisData = redisTemplate.opsForHash().entries(COURIER_KEY);

        Map<String, Object> result = new LinkedHashMap<>();

        if (redisData.isEmpty()) {
            result.put("message", "Courier data not found in Redis");
            return result;
        }

        redisData.forEach((key, value) -> result.put(String.valueOf(key), value));

        return result;
    }
}