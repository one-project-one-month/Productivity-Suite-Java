package com._p1m.productivity_suite.currencyexchange.job;

import com._p1m.productivity_suite.currencyexchange.service.ForexService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Job responsible for refreshing cached forex data based on Central Bank's publishing schedule.
 * The Central Bank publishes rates for the previous day at 22:30 MMT.
 * This job runs daily at 22:35 MMT to fetch and cache the previous day's rates.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ForexCacheRefreshJob {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final ForexService forexService;

    /**
     * Initializes the cache on application startup using the previous day's forex data.
     */
    @PostConstruct
    public void initializeOnStartup() {
        log.info("🚀 [ForexCacheRefreshJob] Initializing forex cache on startup...");
        this.refreshCacheForPreviousDay();
    }

    /**
     * Runs daily at 22:35 MMT to fetch forex rates for the previous day,
     */
    @Scheduled(cron = "0 35 22 * * ?", zone = "Asia/Yangon")
    public void scheduledDailyRefresh() {
        log.info("⏰ [ForexCacheRefreshJob] Triggering scheduled forex cache refresh...");
        this.refreshCacheForPreviousDay();
    }

    /**
     * Core method to perform cache refresh for the previous day's forex data.
     */
    private void refreshCacheForPreviousDay() {
        final String targetDate = LocalDate.now().format(FORMATTER);
        log.info("📦 [ForexCacheRefreshJob] Fetching forex rates for {}", targetDate);

        this.forexService.getForexByDate(targetDate)
                .thenAccept(response -> log.info("✅ [ForexCacheRefreshJob] Cached {} forex rates for {}", response.rates().size(), targetDate))
                .exceptionally(ex -> {
                    log.error("❌ [ForexCacheRefreshJob] Failed to cache forex for {}: {}", targetDate, ex.getMessage(), ex);
                    return null;
                });
    }
}
