package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateLimiter {
    /// Map of <endpoint, limiter>
    private Map<String, Limiter> limiters;
    private Limiter defaultLimiter;

    public RateLimiter(List<Map<String, Object>> configs, Map<String, Object> defaultConfig) {
        this.limiters = new HashMap<>();
        for(Map<String, Object> config: configs) {
            String endpoint = (String) config.get("endpoint");
            if(endpoint == null) continue;
            Limiter limiter = LimiterFactory.create(config);
            limiters.put(endpoint, limiter);
        }

        this.defaultLimiter = LimiterFactory.create(defaultConfig);
    }

    public void reloadConfig(List<Map<String, Object>> configs, Map<String, Object> defaultConfig) {
        Map<String, Limiter> newLimiters = new HashMap<>();
        for(Map<String, Object> config: configs) {
            String endpoint = (String) config.get("endpoint");
            if(endpoint == null) continue;
            Limiter limiter = LimiterFactory.create(config);
            newLimiters.put(endpoint, limiter);
        }

        this.limiters = newLimiters;
        this.defaultLimiter = LimiterFactory.create(defaultConfig);
    }

    public RateLimitResult allow(String clientId, String endpoint) {
        Limiter limiter = limiters.getOrDefault(endpoint, defaultLimiter);
        return limiter.allow(clientId);
    }

    private void evictStaleEntriesPerEndpoint() {
        for (Map.Entry<String, Limiter> limiters: this.limiters.entrySet()) {
            limiters.getValue().evictStaleEntries();
        }
    }
}
