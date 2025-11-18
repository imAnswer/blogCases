package org.example.dynamicProxy.JDKDynamicProxy;

/**
 * 这个类是真正的对象
 * @author liushaoya
 * @since 2025-05-15 10:09
 */
public class BigStar implements Star {
    private String name;

    public BigStar(String name) {
        this.name = name;
    }

    //唱歌
    @Override
    public String sing(String name) {
        System.out.println(this.name + "正在唱歌" + name);
        return "谢谢";
    }

    //跳舞
    @Override
    public void dance() {
        System.out.println(this.name + "正在跳舞");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
