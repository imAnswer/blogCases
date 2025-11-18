package org.example.JMM;

import static java.lang.Thread.sleep;

/**
 * @author liushaoya
 * @since 2025-08-09 19:34
 */
public class Visibility {
    //使用synchronized解决可见性
    /*static boolean run =true;

    final static Object lock = new Object();
    public static void main(String[]args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
                synchronized (lock) {
                    if (!run) {
                        break;
                    }
                }
            }
        });
        t.start();
        Thread.sleep(1000);
        System.out.println("stop");
        synchronized (lock) {
            run =false;// 线程t不会如预想的停下来
        }
    }*/
    //使用volatile解决可见性
    volatile static boolean run =true;
    public static void main(String[]args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
            }
        });
        t.start();
        Thread.sleep(1000);
        System.out.println("stop");
        run =false;// 线程t不会如预想的停下来
    }
}
