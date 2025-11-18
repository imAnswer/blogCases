package org.example.redis.redisson.lock;

/**
 * 分布式锁常量
 * @author liushaoya
 * @since 2025-10-06 18:23
 */

public class DistributeLockConstant {

    public static final String NONE_KEY = "NONE";

    public static final String DEFAULT_OWNER = "DEFAULT";

    public static final int DEFAULT_EXPIRE_TIME = -1;

    public static final int DEFAULT_WAIT_TIME = Integer.MAX_VALUE;
}
