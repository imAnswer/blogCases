package org.example.saToken.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.example.saToken.constant.UserPermission;
import org.example.saToken.constant.UserRole;
import org.example.saToken.constant.UserStateEnum;
import org.example.saToken.dto.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author liushaoya
 * @since 2025-10-12 14:40
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //根据在AuthController中用户登录后绑定的session和设置的数据，取出相关信息
        UserInfo userInfo = (UserInfo) StpUtil.getSessionByLoginId(loginId).get((String) loginId);

        if (userInfo.getState().equals(UserStateEnum.AUTH.name())) {
            return Arrays.asList(UserPermission.AUTH.name());
        }


        return Arrays.asList(UserPermission.NONE.name());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        UserInfo userInfo = (UserInfo) StpUtil.getSessionByLoginId(loginId).get((String) loginId);
        if (userInfo.getUserRole() == UserRole.ADMIN) {
            return Arrays.asList(UserRole.ADMIN.name());
        }
        return Arrays.asList(UserRole.CUSTOMER.name());
    }
}
