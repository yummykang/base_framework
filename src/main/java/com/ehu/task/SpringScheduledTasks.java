package com.ehu.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Spring定时器.
 *
 * @author demon
 * @since 2017-03-03 15:47.
 */
@Configuration
@EnableScheduling
public class SpringScheduledTasks {

    @Scheduled(fixedRate = 100 * 30)
    public void reportCurrentTime() {
        System.out.println("---------------report----------------");
        System.out.println(System.currentTimeMillis());
    }

    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportByCron() {
        System.out.println("---------------report by cron----------------");
        System.out.println(System.currentTimeMillis());
    }
}
