package com._p1m.productivity_suite.currencyexchange.job;

import com._p1m.productivity_suite.currencyexchange.service.ForexService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class LatestForexJob implements Job {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final ForexService forexService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("⏰ [LatestForexJob] Triggered at 07:00 AM to fetch latest forex data...");

        this.forexService.getLatestForex()
                .thenAccept(response -> {
                    final String today = LocalDate.now().format(FORMATTER);
                    log.info("✅ [LatestForexJob] Successfully fetched {} rates for {}", response.rates().size(), today);
                })
                .exceptionally(ex -> {
                    log.error("❌ [LatestForexJob] Failed to fetch latest forex data: {}", ex.getMessage(), ex);
                    return null;
                });
    }
}
