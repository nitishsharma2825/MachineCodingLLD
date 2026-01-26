package models;

import java.util.Map;

public interface Limiter {
    RateLimitResult allow(String clientId);
    void updateConfig(Map<String, Object> newConfig);
    void evictStaleEntries();
}
