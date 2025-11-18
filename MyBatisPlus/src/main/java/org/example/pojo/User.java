package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.SexEnum;

/**
 * @author liushaoya
 * @since 2025-05-08 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String name;

    private Integer age;

    private String email;

    private SexEnum sex;
}
