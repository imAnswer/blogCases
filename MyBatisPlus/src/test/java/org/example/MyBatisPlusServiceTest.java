package org.example;

import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liushaoya
 * @since 2025-05-08 20:28
 */
@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetCount() {
        long count = userService.count();
        System.out.println("count = " + count);
    }

    @Test
    public void testSaveBatch() {
        List<User> list = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName("lsy" + i);
            user.setAge(20 + i);
            list.add(user);
        }
        userService.saveBatch(list);
    }

}
