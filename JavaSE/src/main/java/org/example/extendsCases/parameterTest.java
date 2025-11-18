package org.example.extendsCases;

/**
 * 成员变量访问方式
 * @author liushaoya
 * @since 2025-05-19 16:48
 */
public class parameterTest {
    public static void main(String[] args) {
        Zi z = new Zi();
        z.show();
    }
}

class Fu {
    String name = "Fu";
}

class Zi extends Fu {
    String name = "Zi";
    public void show() {
        String name = "show";
        System.out.println(name);
        System.out.println(this.name);
        System.out.println(super.name);
    }
}