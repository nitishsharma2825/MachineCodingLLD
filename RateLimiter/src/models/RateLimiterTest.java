package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {
    private RateLimiter rateLimiter;
    @BeforeEach
    void setUp() {
        List<Map<String, Object>> configs = new ArrayList<>();

        Map<String, Object> tokenBucketConfig = new HashMap<>();
        tokenBucketConfig.put("endpoint", "/search");
        tokenBucketConfig.put("algorithm", "tokenbucket");
        Map<String, Object> tokenBucketAlgoConfig = new HashMap<>();
        tokenBucketAlgoConfig.put("capacity", 1000);
        tokenBucketAlgoConfig.put("refillRatePerSecond", 10);
        tokenBucketConfig.put("algoConfig", tokenBucketAlgoConfig);

        Map<String, Object> slidingWindowConfig = new HashMap<>();
        slidingWindowConfig.put("endpoint", "/find");
        slidingWindowConfig.put("algorithm", "slidingwindow");
        Map<String, Object> slidingWindowAlgoConfig = new HashMap<>();
        slidingWindowAlgoConfig.put("maxRequests", 1000);
        slidingWindowAlgoConfig.put("windowMs", 1000);
        slidingWindowConfig.put("algoConfig", slidingWindowAlgoConfig);

        configs.add(tokenBucketConfig);
        configs.add(slidingWindowConfig);

        this.rateLimiter = new RateLimiter(configs, tokenBucketConfig);
    }

    @Test
    void testRateLimiter() {
        for(int i =0; i< 10000; i++){
            RateLimitResult result = this.rateLimiter.allow("1", "/search");
            if (!result.isAllowed()) {
                System.out.println(result.toString());
                break;
            }
        }
    }

    @Test
    void testConcurrency() throws InterruptedException {
        // to make all threads wait till all are ready
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(10);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 100000; i++) {
                startLatch.await();
                executor.submit(() -> {
                    RateLimitResult result = this.rateLimiter.allow("1", "/search");
                });
                endLatch.countDown();
            }
        } catch (Exception e) {

        }

        startLatch.countDown();
        endLatch.await();
        executor.shutdown();
    }
}