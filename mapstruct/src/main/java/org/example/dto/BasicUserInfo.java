package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liushaoya
 * @since 2025-10-12 21:52
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserInfo implements Serializable {

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
}
