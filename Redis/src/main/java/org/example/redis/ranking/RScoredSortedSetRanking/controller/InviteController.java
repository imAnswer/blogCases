package org.example.redis.ranking.RScoredSortedSetRanking.controller;

import org.example.redis.ranking.RScoredSortedSetRanking.entity.InviteRankInfo;
import org.example.redis.ranking.RScoredSortedSetRanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @author liushaoya
 * @since 2025-10-15 14:14
 */
@RestController
public class InviteController {
    @Autowired
    private UserService userService;

    @GetMapping("/getTopN")
    public List<InviteRankInfo> getTopN(@Max(100) Integer topN) {
        if (topN == null) {
            topN = 100;
        }

        return userService.getTopN(topN);
    }

}
