package com._p1m.productivity_suite.currencyexchange.config;

//import com._p1m.productivity_suite.currencyexchange.job.ForexCacheRefreshJob;
import com._p1m.productivity_suite.currencyexchange.job.LatestForexJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class QuartzJobConfig {

//    @Bean
//    public JobDetail forexCacheRefreshJobDetail() {
//        return JobBuilder.newJob(ForexCacheRefreshJob.class)
//                .withIdentity("forexCacheRefreshJob")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger forexCacheRefreshTrigger(final JobDetail forexCacheRefreshJobDetail) {
//        return TriggerBuilder.newTrigger()
//                .forJob(forexCacheRefreshJobDetail)
//                .withIdentity("forexCacheRefreshTrigger")
//                .withSchedule(CronScheduleBuilder
//                        .cronSchedule("0 35 22 * * ?")
//                        .inTimeZone(TimeZone.getTimeZone("Asia/Yangon"))
//                )
//                .build();
//    }

    @Bean
    public JobDetail latestForexJobDetail() {
        return JobBuilder.newJob(LatestForexJob.class)
                .withIdentity("latestForexJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger latestForexTrigger(final JobDetail latestForexJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(latestForexJobDetail)
                .withIdentity("latestForexTrigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0 0 7 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Yangon"))
                )
                .build();
    }
}
