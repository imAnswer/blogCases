package org.example.redis.ranking;

import org.example.redis.ranking.zsetRanking.ZsetRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author liushaoya
 * @since 2025-10-25 16:13
 */
@RestController
public class RankingController {
    @Autowired
    private ZsetRankingService zsetRankingService;

    @GetMapping("/zsetRankByRange")
    public Set<String> zsetTopNByRange(int n){
        return zsetRankingService.getTopNByRange(n);
    }

    @GetMapping("/zsetRankByReversedRange")
    public List<String> zsetTopNByReversedRange(int n){
        System.out.println(zsetRankingService.getTopNByReverseRangeWithScores(n));
        return zsetRankingService.getTopNByReverseRangeWithScores(n);
    }
}
