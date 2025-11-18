package org.example.redis.redisson.limiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-10-06 15:41
 */
@RestController
public class LimiterController {
    @Autowired
    private LimiterService test;

    @GetMapping("/limiter")
    public void test() {
        test.test();
    }

}
