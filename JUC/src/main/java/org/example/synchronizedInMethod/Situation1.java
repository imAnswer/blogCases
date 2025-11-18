package org.example.synchronizedInMethod;

/**
 * @author liushaoya
 * @since 2025-10-27 10:06
 * 下面这种情况锁的是同一个this对象，会发生锁竞争
 * 运行结果：1 2或2 1
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Situation1{
    public synchronized void a() {
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
    public static void main(String[] args) {
        Situation1 n1 = new Situation1();
        new Thread(()->{ n1.a(); }).start();
        new Thread(()->{ n1.b(); }).start();
    }
}

