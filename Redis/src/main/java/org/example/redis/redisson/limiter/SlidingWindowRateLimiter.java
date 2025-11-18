package org.example.redis.redisson.limiter;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liushaoya
 * @since 2025-10-05 22:23
 */
public class SlidingWindowRateLimiter implements RateLimiter {

    @Autowired
    private RedissonClient redissonClient;

    private static final String LIMIT_KEY_PREFIX = "nft:turbo:limit:";


    @Override
    public Boolean tryAcquire(String key, int limit, int windowSize) {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(LIMIT_KEY_PREFIX + key);

        if(!rRateLimiter.isExists()) {
            rRateLimiter.trySetRate(RateType.OVERALL, limit, windowSize, RateIntervalUnit.SECONDS);
        }

        return rRateLimiter.tryAcquire();
    }
}
