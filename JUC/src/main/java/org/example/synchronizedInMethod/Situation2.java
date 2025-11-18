package org.example.synchronizedInMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Situation2{
    public synchronized void a() throws InterruptedException {
        Thread.sleep(1000);
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }

    public static void main(String[] args) {
        Situation2 n2 = new Situation2();
        new Thread(()->{
            try {
                n2.a();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{ n2.b(); }).start();
    }
}

