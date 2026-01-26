package models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketLimiter implements Limiter {
    private int capacity;
    private int refillRatePerSecond;
    private ConcurrentHashMap<String, TokenBucket> buckets;
    private ReentrantLock lock = new ReentrantLock();

    private static class TokenBucket {
        double tokens;
        long lastRefillTime;

        TokenBucket(double initialTokens, long time) {
            this.tokens = initialTokens;
            this.lastRefillTime = time;
        }
    }

    public TokenBucketLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRate;
        this.buckets = new ConcurrentHashMap<>();
    }

    @Override
    public RateLimitResult allow(String clientId) {
        TokenBucket bucket = buckets.get(clientId);
        if (bucket == null) {
            bucket = new TokenBucket(this.capacity, System.currentTimeMillis());
            buckets.put(clientId, bucket);
        }

        // Calculate result values while holding lock on bucket
        boolean allowed;
        int remaining;
        Long retryAfterMs;

        // other approach would be to maintain a map<bucket, lock>
        synchronized (bucket) {
            long now = System.currentTimeMillis();
            long elapsed = now - bucket.lastRefillTime;
            double tokensToAdd = (elapsed * refillRatePerSecond) / 1000.0;
            bucket.tokens = Math.min(capacity, bucket.tokens + tokensToAdd);
            bucket.lastRefillTime = now;

            if (bucket.tokens >= 1) {
                bucket.tokens -= 1;
                allowed = true;
                remaining = (int) Math.floor(bucket.tokens);
                retryAfterMs = null;
            } else {
                allowed = false;
                remaining = 0;
                retryAfterMs = (long) (refillRatePerSecond / 1000);
            }
        }

        return new RateLimitResult(allowed, remaining, retryAfterMs);
    }

    @Override
    public void updateConfig(Map<String, Object> newConfig) {
        lock.lock();
        // update the config and each bucket
        lock.unlock();
    }

    @Override
    public void evictStaleEntries() {

    }
}
