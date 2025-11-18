package org.example.saToken.dto;

import lombok.*;
import org.example.saToken.constant.UserRole;
import org.example.saToken.constant.UserStateEnum;

import java.util.Date;

/**
 * @author liushaoya
 * @since 2025-10-12 14:41
 */
@Data
@Builder
public class UserInfo{

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 状态
     *
     * @see UserStateEnum
     */
    private String state;

    /**
     * 区块链地址
     */
    private String blockChainUrl;


    /**
     * 用户角色
     */
    private UserRole userRole;


}
