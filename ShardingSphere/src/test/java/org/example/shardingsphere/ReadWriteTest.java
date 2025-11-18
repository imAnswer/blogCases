package org.example.shardingsphere;

import org.example.shardingsphere.entity.User;
import org.example.shardingsphere.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liushaoya
 * @since 2025-06-18 16:46
 */
@SpringBootTest
public class ReadWriteTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 写入数据的测试
     */
    @Test
    public void testInsert(){

        User user = new User();
        user.setUname("张三丰");
        userMapper.insert(user);
    }

}
