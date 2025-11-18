package org.example;

import org.example.dto.User;
import org.example.dto.UserInfo;
import org.example.dto.UserRole;
import org.example.dto.UserStateEnum;

import java.util.Date;

/**
 * @author liushaoya
 * @since 2025-10-12 21:41
 */
public class Main {
    public static void main(String[] args) {
        // 1) 构造一个源对象 User（确保 User 有 getter/setter 或加上 @Data）
        User u = new User();
        u.setId(1001L);
        u.setNickName("Alice");
        u.setTelephone("13812348899");
        u.setInviteCode("INV-9X3K");
        u.setProfilePhotoUrl("https://img.example.com/u/1001.png");
        u.setBlockChainUrl("0x9a3b...cdef");
        u.setBlockChainPlatform("Ethereum");
        u.setCertification(Boolean.TRUE);
        u.setUserRole(UserRole.ADMIN);      // 枚举示例，按你实际的枚举值
        u.setState(UserStateEnum.ACTIVE);    // 枚举示例，按你实际的枚举值
        u.setGmtCreate(new Date());
        u.setGmtModified(new Date());
        u.setLastLoginTime(new Date());

        // 2) 调用 MapStruct 的转换
        UserInfo vo = UserConvertor.INSTANCE.mapToVo(u);

        // 3) 打印结果
        System.out.println("== Convert Result ==");
        System.out.println("userId       : " + vo.getUserId());          // 来自 User.id
        System.out.println("nickName     : " + vo.getNickName());        // 同名自动映射
        System.out.println("telephone    : " + vo.getTelephone());
        System.out.println("profilePhoto : " + vo.getProfilePhotoUrl());
        System.out.println("blockChain   : " + vo.getBlockChainUrl() + " / " + vo.getBlockChainPlatform());
        System.out.println("certification: " + vo.getCertification());
        System.out.println("userRole     : " + vo.getUserRole());
        System.out.println("state        : " + vo.getState());           // enum -> String（默认取 name）
        System.out.println("createTime   : " + vo.getCreateTime());      // 来自 User.gmtCreate
    }
}
