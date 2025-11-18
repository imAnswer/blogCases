package org.example.timerTask;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liushaoya
 * @since 2025-08-22 18:52
 */
public class TestSchedule {
    //如何让每周四 18：00定时执行任务
    public static void main(String[] args) {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();

        //获取周四时间
        LocalDateTime time = now
                .withHour(19)   // 把小时改为 19
                .withMinute(4)  // 分钟改为 04
                .withSecond(50)  // 秒改为 50
                .withNano(0)    // 纳秒改为 0
                .with(DayOfWeek.FRIDAY); // 替换成周五

        //如果 当前时间 > 本周周五，必须找到下周周五
        if(now.compareTo(time) > 0) {
            time.plusWeeks(1);
        }

        //间隔时间
        long initialDelay = Duration.between(now, time).toMillis();

        //initialDelay代表当前时间和周五的时间差
        //period一周的间隔时间
        long period = 1000;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {
            System.out.println("running");
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
