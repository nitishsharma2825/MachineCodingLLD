package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    // Map of <endpoint, limiter>
    // preserves happens before relationship before and after volatile writes
    private volatile Map<String, Limiter> limiters;
    private volatile Limiter defaultLimiter;
    private ScheduledExecutorService executor;

    public RateLimiter(List<Map<String, Object>> configs, Map<String, Object> defaultConfig) {
        this.limiters = new HashMap<>();
        for(Map<String, Object> config: configs) {
            String endpoint = (String) config.get("endpoint");
            if(endpoint == null) continue;
            Limiter limiter = LimiterFactory.create(config);
            limiters.put(endpoint, limiter);
        }

        this.defaultLimiter = LimiterFactory.create(defaultConfig);

//        Thread evictionThread = new Thread(() -> {
//            while(true) {
//                evictStaleEntriesPerEndpoint();
//            }
//        });
//
//        evictionThread.setDaemon(false);
//        evictionThread.start();

//        this.executor = Executors.newFixedThreadPool(1);
//        executor.submit(() -> {
//            while (true) {
//                Thread.sleep(1000);
//                evictStaleEntriesPerEndpoint();
//            }
//        });

        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleWithFixedDelay(
                this::evictStaleEntriesPerEndpoint,
                1,
                60,
                TimeUnit.SECONDS
        );
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

        // other approach
        // for each limiter, call evictEntries() and handle eviction inside that
        // this will help preserve per clientID state
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

    public void shutdown() {
        this.executor.shutdown();
    }
}
