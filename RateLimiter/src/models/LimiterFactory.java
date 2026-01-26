package models;

import java.util.Collections;
import java.util.Map;

public class LimiterFactory {
    @SuppressWarnings("unchecked")
    public static Limiter create(Map<String, Object> config) {
        String algorithm = (String) config.get("algorithm");
        Map<String, Object> algoConfig = (Map<String, Object>) config.get("algoConfig");
        if(algoConfig == null) {
            algoConfig = Collections.emptyMap();
        }

        switch (algorithm.toLowerCase()) {
            case "tokenbucket":
                int capacity = ((Number) algoConfig.getOrDefault("capacity", 0)).intValue();
                int refillRate = ((Number) algoConfig.getOrDefault("refillRatePerSecond", 0)).intValue();
                return new TokenBucketLimiter(capacity, refillRate);
            case "slidingwindow":
                int maxRequests = ((Number) algoConfig.getOrDefault("maxRequests", 0)).intValue();
                int windowMs = ((Number) algoConfig.getOrDefault("windowMs", 0)).intValue();
                return new SlidingWindowLogLimiter(maxRequests, windowMs);
            default:
                throw new IllegalArgumentException("Unknown algo: " + algorithm);
        }
    }
}
