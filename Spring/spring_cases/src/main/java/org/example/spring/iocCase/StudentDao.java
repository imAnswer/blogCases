package org.example.spring.iocCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author liushaoya
 * @since 2024-12-10 13:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StudentDao {
    private String name;
    private int age;

    public String getMessage() {
        return "测试成功";
    }

}
