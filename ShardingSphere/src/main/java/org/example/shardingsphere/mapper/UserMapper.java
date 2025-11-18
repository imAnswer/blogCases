package org.example.shardingsphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.shardingsphere.entity.User;

/**
 *
 *@author liushaoya
 *@since 2025-06-18 15:14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}