package org.example.usefulMethod;

/**
 * @author liushaoya
 * @since 2025-08-05 21:39
 */
public class Interrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("开始");
                Thread.sleep(5000);
                System.out.println("结束");
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
        },"t1");
        t1.start();
//        System.out.println(t1.isInterrupted());
        Thread.sleep(1000);

        t1.interrupt();
        System.out.println(t1.isInterrupted());
    }
}
