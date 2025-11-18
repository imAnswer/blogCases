package org.example.redis.cached;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-10-08 20:58
 */
@RestController
public class CachedController {

    @GetMapping("/cached")
    @Cached(name = ":user:cache:id:", cacheType = CacheType.REMOTE, key = "#userId", cacheNullValue = true)
    public String test(Long userId) {
        System.out.println("调用数据库");
        return "用户id" + userId;
    }

}
