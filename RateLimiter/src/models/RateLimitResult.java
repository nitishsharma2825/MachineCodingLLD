package models;

public class RateLimitResult {
    private boolean allowed;
    private int remaining;
    private Long retryAfterMs;

    public RateLimitResult(boolean allowed, int remaining, Long retryAfterMs) {
        this.allowed = allowed;
        this.remaining = remaining;
        this.retryAfterMs = retryAfterMs;
    }

    @Override
    public String toString() {
        return "RateLimitResult{" +
                "allowed=" + allowed +
                ", remaining=" + remaining +
                ", retryAfterMs=" + retryAfterMs +
                '}';
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getRemaining() {
        return remaining;
    }

    public Long getRetryAfterMs() {
        return retryAfterMs;
    }
}
