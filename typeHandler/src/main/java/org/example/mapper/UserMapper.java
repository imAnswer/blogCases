package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.User;

/**
 * @author liushaoya
 * @since 2025-10-14 19:32
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    void saveUser(User user);

    User getUser(Long id);
}
