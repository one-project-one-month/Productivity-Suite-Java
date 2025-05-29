//package com._p1m.productivity_suite.currencyexchange.job;
//
//import com._p1m.productivity_suite.currencyexchange.service.ForexService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ForexCacheRefreshJob implements Job {
//
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//    private final ForexService forexService;
//
//    @Override
//    public void execute(final JobExecutionContext context) {
//        final String targetDate = LocalDate.now().format(FORMATTER);
//        log.info("📦 [Quartz] Fetching forex rates for {}", targetDate);
//
//        this.forexService.getForexByDate(targetDate)
//                .thenAccept(response -> log.info("✅ [Quartz] Cached {} forex rates for {}", response.rates().size(), targetDate))
//                .exceptionally(ex -> {
//                    log.error("❌ [Quartz] Failed to cache forex for {}: {}", targetDate, ex.getMessage(), ex);
//                    return null;
//                });
//    }
//}
