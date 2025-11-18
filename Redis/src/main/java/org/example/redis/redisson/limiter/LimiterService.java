package org.example.redis.redisson.limiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2025-10-06 15:34
 */
@Service
public class LimiterService {
    @Autowired
    private SlidingWindowRateLimiter slidingWindowRateLimiter;
    public void test() {
        Boolean result = slidingWindowRateLimiter.tryAcquire("testLock997", 3, 10);
        System.out.println(result);
        result = slidingWindowRateLimiter.tryAcquire("testLock997", 3, 10);
        System.out.println(result);
        result = slidingWindowRateLimiter.tryAcquire("testLock997", 3, 10);
        System.out.println(result);
        result = slidingWindowRateLimiter.tryAcquire("testLock997", 3, 10);
        System.out.println(result);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        result = slidingWindowRateLimiter.tryAcquire("testLock997", 3, 10);
        System.out.println(result);
    }


}

