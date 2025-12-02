package org.example.inventory.idUtil;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

/**
 * @author Hollis
 */
public class WorkerIdHolder implements CommandLineRunner {

    private RedissonClient redissonClient;

    @Value("${order.client.name:workerId}")
    private String clientName;

    public static long WORKER_ID;

    public WorkerIdHolder(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void run(String... args) throws Exception {
        //获取一个名为 clientName 的 分布式原子长整型
        RAtomicLong atomicLong = redissonClient.getAtomicLong(clientName);
        //incrementAndGet()：先加 1 再返回。如果这个键之前不存在，相当于从 0 变 1
        WORKER_ID = atomicLong.incrementAndGet() % 32;
    }
}
