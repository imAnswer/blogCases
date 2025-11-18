package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.houbb.sensitive.annotation.strategy.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 需要脱敏的类
 * @author liushaoya
 * @since 2025-10-15 9:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sensitivetest")
public class SensitiveTest {
    private Long id;

    @SensitiveStrategyChineseName
    private String username;

    @SensitiveStrategyPassword
    private String password;

    @SensitiveStrategyPassport
    private String passport;

    @SensitiveStrategyIdNo
    private String idNo;

    @SensitiveStrategyCardId
    private String bandCardId;

    @SensitiveStrategyPhone
    private String phone;

    @SensitiveStrategyEmail
    private String email;

    @SensitiveStrategyAddress
    private String address;

    @SensitiveStrategyBirthday
    private String birthday;

    @SensitiveStrategyGps
    private String gps;

    @SensitiveStrategyIp
    private String ip;

    @SensitiveStrategyMaskAll
    private String maskAll;

    @SensitiveStrategyMaskHalf
    private String maskHalf;

    @SensitiveStrategyMaskRange
    private String maskRange;
}
