package org.example.redis.ranking.RScoredSortedSetRanking.service;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import io.micrometer.core.instrument.util.StringUtils;
import org.example.redis.ranking.RScoredSortedSetRanking.entity.InviteRankInfo;
import org.example.redis.ranking.RScoredSortedSetRanking.entity.User;
import org.example.redis.ranking.RScoredSortedSetRanking.mapper.UserMapper;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liushaoya
 * @since 2025-10-15 14:10
 */
@Service
public class UserService implements InitializingBean {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonClient redissonClient;

    private RScoredSortedSet<String> inviteRank;


    public List<InviteRankInfo> getTopN(Integer topN) {
        Collection<ScoredEntry<String>> rankInfos = inviteRank.entryRangeReversed(0, topN - 1);

        List<InviteRankInfo> inviteRankInfos = new ArrayList<>();

        if (rankInfos != null) {
            for (ScoredEntry<String> rankInfo : rankInfos) {
                InviteRankInfo inviteRankInfo = new InviteRankInfo();
                String userId = rankInfo.getValue();
                if (StringUtils.isNotBlank(userId)) {
                    User user = findById(Long.valueOf(userId));
                    if (user != null) {
                        inviteRankInfo.setNickName(user.getNickName());
                        inviteRankInfo.setInviteCode(user.getInviteCode());
                        inviteRankInfo.setInviteScore(rankInfo.getScore().intValue());
                        inviteRankInfos.add(inviteRankInfo);
                    }
                }
            }
        }

        return inviteRankInfos;
    }

    @Cached(name = ":user:cache:id:", cacheType = CacheType.BOTH, key = "#userId", cacheNullValue = true)
    @CacheRefresh(refresh = 60, timeUnit = TimeUnit.MINUTES)
    public User findById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public void afterPropertiesSet() {
        this.inviteRank = redissonClient.getScoredSortedSet("inviteRank");
        inviteRank.add(123.732, "36");
        inviteRank.add(135.102, "29");
    }
}
