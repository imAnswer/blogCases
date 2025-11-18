package org.example;
/**
 *
 *@author liushaoya
 *@since 2024-12-05 21:20
 */
public class Demo1 {

    ThreadLocal<String> tl = new ThreadLocal<>();

    //变量
    private String content;

    private String getContent() {
//        return content;
        String s = tl.get();
        return s;
    }

    private void setContent(String content) {
//        this.content = content;
        //变量content绑定到当前线程
        tl.set(content);
    }

    public static void main(String[] args) {
        Demo1 demo = new Demo1();

        for (int i = 0; i < 5; i++) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    /*
                        每个线程: 存一个变量 , 过一会 取出这个变量
                     */
                    demo.setContent(Thread.currentThread().getName() + "的数据");
                    System.out.println("-----------------------");
                    System.out.println(Thread.currentThread().getName() + "--->" + demo.getContent());
                }
            });

            thread.setName("线程" + i); //线程0~4
            thread.start();
        }
    }

}

