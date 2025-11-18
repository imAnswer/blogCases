package org.example.redis.ranking.RScoredSortedSetRanking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.redis.ranking.RScoredSortedSetRanking.entity.User;

/**
 * @author liushaoya
 * @since 2025-10-15 14:23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
