package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.AesEncryptTypeHandler;

/**
 * @author liushaoya
 * @since 2025-10-14 19:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "user", autoResultMap = true)
public class User {
    private Long id;
    private String name;
    private String phone;
    @TableField(typeHandler = AesEncryptTypeHandler.class)
    private String password;
}
