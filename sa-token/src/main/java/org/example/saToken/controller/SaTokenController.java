package org.example.saToken.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.example.saToken.constant.UserRole;
import org.example.saToken.constant.UserStateEnum;
import org.example.saToken.dto.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-10-07 21:02
 */
/**
 * 登录测试
 */
@RestController
public class SaTokenController {
    @GetMapping("/login")
    public String login() {
        UserInfo userInfo = UserInfo.builder()
                .userId(1L)
                .state(UserStateEnum.AUTH.name())
                .userRole(UserRole.ADMIN)
                .build();

        //获取登录的token令牌
        StpUtil.login(userInfo.getUserId());

        //将用户信息存储到session中
        StpUtil.getSession().set(userInfo.getUserId().toString(), userInfo);
        return "登录成功";
    }

    @GetMapping("/role1")
    public String roleTest1() {

        return "访问成功";
    }

    @GetMapping("/role2")
    public String roleTest2() {

        return "访问成功";
    }

}

