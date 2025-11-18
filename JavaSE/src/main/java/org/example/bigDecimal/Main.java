package org.example.bigDecimal;

import java.math.BigDecimal;

/**
 * @author liushaoya
 * @since 2025-10-04 20:17
 */
public class Main {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(10000).setScale(-2);
        BigDecimal b = new BigDecimal(1.02);
        System.out.println(b);

        // 想把金额转成分
        double amountYuan = 1.234;           // 1 元 2 角 3 分 4 厘
        long amountInFen = Math.round(amountYuan * 100); // 转成分
        System.out.println(amountInFen);

    }
}
