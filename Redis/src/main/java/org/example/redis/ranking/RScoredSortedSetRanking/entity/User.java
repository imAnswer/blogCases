package org.example.redis.ranking.RScoredSortedSetRanking.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.redis.ranking.RScoredSortedSetRanking.constant.UserRole;
import org.example.redis.ranking.RScoredSortedSetRanking.constant.UserStateEnum;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class User extends BaseEntity {
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String passwordHash;

    /**
     * 状态
     */
    private UserStateEnum state;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请人用户ID
     */
    private String inviterId;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 头像地址
     */
    private String profilePhotoUrl;

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
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证hash
     */
    private String idCardNo;

    /**
     * 用户角色
     */
    private UserRole userRole;

}