package org.example.dto;

import lombok.*;

import java.util.Date;

/**
 * @author liushaoya
 * @since 2025-10-12 21:45
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String profilePhotoUrl;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 状态
     *
     */
    private String state;

    /**
     * 区块链地址
     */
    private String blockChainUrl;

    /**
     * 区块链平台
     */
    private String blockChainPlatform;

    /**
     * 实名认证
     */
    private Boolean certification;

    /**
     * 用户角色
     */
    private UserRole userRole;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 注册时间
     */
    private Date createTime;

}
