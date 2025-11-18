package org.example.redis.ranking.zsetRanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;

/**
 * @author liushaoya
 * @since 2025-10-25 15:52
 */
@Service
public class ZsetRankingService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 排行榜的 Redis Key
     */
    private static final String KEY = "lb:game:daily";


    /**
     * 获取Top N（基于range方法实现）
     * 从小到大排序且只返回member不返回score
     */
    public Set<String> getTopNByRange(int n) {
        return stringRedisTemplate.opsForZSet().range(KEY, 0, n - 1);
    }

    /**
     * 获取Top N（基于reverseRangeWithScores实现）
     * 从大到小排序且返回member和score
     */
    public List<String> getTopNByReverseRangeWithScores(int n) {
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet().reverseRangeWithScores(KEY, 0, n - 1);
        List<String> rank = new ArrayList<>();
        if(tuples != null) {
            for(ZSetOperations.TypedTuple<String> tuple : tuples){
                rank.add(tuple.getValue() + ":" + tuple.getScore());
            }
        }
        return rank;
    }


    @PostConstruct
    public void init() {
        // mock 的用户分数（member -> score）
        Map<String, Double> mock = new LinkedHashMap<>();
        mock.put("u:1001", 3520.0);
        mock.put("u:1002", 1980.0);
        mock.put("u:1003", 4210.0);
        // 与 u:1003 分数相同，ZSet 会按 member 字典序做二级排序
        mock.put("u:1004", 4210.0);
        mock.put("u:1005", 2600.0);
        mock.put("u:1006", 800.0);
        mock.put("u:1007", 3050.0);
        mock.put("u:1008", 1200.0);

        stringRedisTemplate.delete(KEY);
        ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
        mock.forEach((member, score) -> zset.add(KEY, member, score));
        // 可选：设置过期时间
         stringRedisTemplate.expire(KEY, Duration.ofMinutes(30));
    }

}
