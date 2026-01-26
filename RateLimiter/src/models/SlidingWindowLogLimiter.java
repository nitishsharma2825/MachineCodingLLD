package models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowLogLimiter implements Limiter {
    private int maxRequests;
    private long windowMs;
    private Map<String, RequestLog> logs = new HashMap<>();
    private ReentrantLock lock = new ReentrantLock();
    @Override
    public RateLimitResult allow(String clientId) {
        RequestLog log = this.logs.get(clientId);
        if (log == null) {
            log = new RequestLog();
            logs.put(clientId, log);
        }

        long now = System.currentTimeMillis();
        long cutoff = now - windowMs;

        while(!log.timestamps.isEmpty() && log.timestamps.peekFirst() < cutoff) {
            log.timestamps.pollFirst();
        }

        if (log.timestamps.size() < maxRequests) {
            log.timestamps.addLast(now);
            int remaining = maxRequests - log.timestamps.size();
            return new RateLimitResult(true, remaining, null);
        }

        long retryAfterMs = log.timestamps.peekFirst() - cutoff;
        return new RateLimitResult(false, 0, retryAfterMs);
    }

    @Override
    public void updateConfig(Map<String, Object> newConfig) {
        lock.lock();
        // update the config and each bucket
        lock.unlock();
    }

    @Override
    public void evictStaleEntries() {
        System.out.println("evicting stale entries for sliding window");
    }

    private static class RequestLog {
        private LinkedList<Long> timestamps = new LinkedList<>();
    }

    public SlidingWindowLogLimiter(int maxRequests, long windowMs) {
        this.maxRequests = maxRequests;
        this.windowMs = windowMs;
    }

}
