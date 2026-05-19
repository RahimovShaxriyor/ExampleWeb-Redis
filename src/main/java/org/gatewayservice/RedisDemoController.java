package org.gatewayservice;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class RedisDemoController {

    private final RedisDemoService redisDemoService;

    public RedisDemoController(RedisDemoService redisDemoService) {
        this.redisDemoService = redisDemoService;
    }

    @PostMapping("/username")
    public Map<String, Object> saveUsername(@RequestParam String username) {
        redisDemoService.saveUsername(username);

        return Map.of(
                "message", "Name saved in Redis",
                "redisKey", "demo:username",
                "value", username
        );
    }

    @GetMapping("/username")
    public Map<String, Object> getUsername() {
        String username = redisDemoService.getUsername();

        return Map.of(
                "redisKey", "demo:username",
                "value", username == null ? "Not found" : username
        );
    }

    @DeleteMapping("/username")
    public Map<String, Object> deleteUsername() {
        redisDemoService.deleteUsername();

        return Map.of(
                "message", "Name deleted from Redis",
                "redisKey", "demo:username"
        );
    }

    @PostMapping("/code")
    public Map<String, Object> saveCode(@RequestParam String code,
                                        @RequestParam long seconds) {
        redisDemoService.saveCodeWithTtl(code, seconds);

        return Map.of(
                "message", "Temporary code saved in Redis",
                "redisKey", "demo:verification-code",
                "value", code,
                "ttlSeconds", seconds
        );
    }

    @GetMapping("/code")
    public Map<String, Object> getCode() {
        String code = redisDemoService.getCode();
        Long ttl = redisDemoService.getCodeTtl();

        return Map.of(
                "redisKey", "demo:verification-code",
                "value", code == null ? "Expired or not found" : code,
                "ttlSeconds", ttl
        );
    }

    @PostMapping("/counter/increment")
    public Map<String, Object> incrementCounter() {
        Long value = redisDemoService.incrementCounter();

        return Map.of(
                "message", "Counter increased",
                "redisKey", "demo:click-counter",
                "value", value
        );
    }

    @PostMapping("/counter/reset")
    public Map<String, Object> resetCounter() {
        redisDemoService.resetCounter();

        return Map.of(
                "message", "Counter reset",
                "redisKey", "demo:click-counter",
                "value", 0
        );
    }

    @GetMapping("/counter")
    public Map<String, Object> getCounter() {
        return Map.of(
                "redisKey", "demo:click-counter",
                "value", redisDemoService.getCounter()
        );
    }

    @PostMapping("/courier/status")
    public Map<String, Object> updateCourierStatus(@RequestParam String status) {
        redisDemoService.updateCourierStatus(status);

        return Map.of(
                "message", "Courier status updated in Redis",
                "redisKey", "demo:courier:1",
                "status", status
        );
    }

    @GetMapping("/courier")
    public Map<String, Object> getCourierInfo() {
        return redisDemoService.getCourierInfo();
    }
}