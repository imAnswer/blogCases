package org.example.controller;

import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-10-14 19:26
 */
@RestController
public class TypeHandlerController {
    @Autowired
    UserMapper userMapper;

    /**
     * 基于注解方式以加密形式插入数据（这类用于使用mybatis自带方法，不依赖自己手动写xml的方法）
     * @return
     */
    @GetMapping("/saveAnnotation")
    public String saveAnnotation() {
        User user = User.builder()
                .id(1L)
                .name("张三")
                .phone("1231323131")
                .password("zhangsan")
                .build();
        userMapper.insert(user);
        return "插入成功";
    }

    /**
     * 基于注解方式以解密方式取出数据（这类用于使用mybatis自带方法，不依赖自己手动写xml的方法）
     * @return
     */
    @GetMapping("/getAnnotation")
    public String getAnnotation() {
        User user = userMapper.selectById(1L);
        System.out.println(user.getPassword());
        return "查询成功";
    }

    /**
     * 基于自定义处理器以加密方式插入数据（这类用于不使用mybatis自带方法，以自己手动写xml的方法）
     * @return
     */
    @GetMapping("/saveXml")
    public String saveXml() {
        User user = User.builder()
                .id(2L)
                .name("李四")
                .phone("1231323131")
                .password("lisi")
                .build();
        userMapper.saveUser(user);
        return "插入成功";
    }

    /**
     * 基于自定义处理器以解密方式取出数据（这类用于不使用mybatis自带方法，以自己手动写xml的方法）
     */
    @GetMapping("/getXml")
    public String getXml() {
        User user = userMapper.getUser(2L);
        System.out.println(user.getPassword());
        return "查询成功";
    }
}
