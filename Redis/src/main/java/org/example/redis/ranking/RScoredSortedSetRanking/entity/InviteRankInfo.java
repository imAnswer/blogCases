package org.example.redis.ranking.RScoredSortedSetRanking.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liushaoya
 * @since 2025-10-15 14:12
 */
@Setter
@Getter
public class InviteRankInfo {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请积分
     */
    private Integer inviteScore;
}
