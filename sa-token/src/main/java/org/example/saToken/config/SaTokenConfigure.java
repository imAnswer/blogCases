package org.example.saToken.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.example.saToken.constant.UserPermission;
import org.example.saToken.constant.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sa-token的全局配置
 * @author liushaoya
 * @since 2025-10-07 21:07
 */
@Configuration
public class SaTokenConfigure {
    @Bean
    public SaServletFilter saServletFilter() {
        return new SaServletFilter()
                // 需要过滤的路径
                .addInclude("/**")
                // 直接放行的路径（完全不进入 setAuth）
                .addExclude("/favicon.ico")
                // 鉴权逻辑（每次请求都会执行）
                .setAuth(r -> {
                    // 1) 登录校验：除 /login 外都要求已登录
                    SaRouter.match("/**")
                            .notMatch("/login")
                            .check(rr -> StpUtil.checkLogin());

                    // 2) 细分鉴权
                    SaRouter.match("/role1", rr -> StpUtil.checkRole(UserRole.ADMIN.name()));
                    SaRouter.match("/role2", rr -> StpUtil.checkPermission(UserPermission.FROZEN.name()));
                    SaRouter.match("/order/**", rr -> StpUtil.checkPermissionOr(
                            UserPermission.BASIC.name(), UserPermission.FROZEN.name()));
                });
    }

}
