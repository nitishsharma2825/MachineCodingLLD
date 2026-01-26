import models.RateLimitResult;
import models.RateLimiter;

import java.util.*;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
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

        RateLimiter limiter = new RateLimiter(configs, tokenBucketConfig);

        while (!exit) {
            String[] command = sc.nextLine().split(" ");
            if (command[0].equals("accept")) {
                RateLimitResult result = limiter.allow(command[1], command[2]);
                System.out.println(result.toString());
            } else {
                System.out.println("Invalid command. exiting");
                exit = true;
            }
        }
    }
}
