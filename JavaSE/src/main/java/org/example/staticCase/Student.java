package org.example.staticCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liushaoya
 * @since 2025-05-16 20:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private int age;
    private String gender;

    public void study() {
        System.out.println(name + "正在学习");
    }

    public void show() {
        System.out.println("姓名：" + name + "，年龄：" + age + "，性别：" + gender);
    }

}
