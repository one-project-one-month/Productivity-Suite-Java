package com._p1m.productivity_suite.currencyexchange.runner;

import com._p1m.productivity_suite.currencyexchange.service.ForexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class ForexStartupCacheInitializer implements ApplicationRunner {

    private final ForexService forexService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void run(ApplicationArguments args) {
//        final String today = LocalDate.now().format(FORMATTER);
//        log.info("🚀 [Startup] Initializing forex cache for {}", today);
//        this.forexService.getForexByDate(today)
//            .thenAccept(response -> log.info("✅ [Startup] Cached {} forex rates for {}", response.rates().size(), today))
//            .exceptionally(ex -> {
//                log.error("❌ [Startup] Failed to cache forex on startup: {}", ex.getMessage(), ex);
//                return null;
//            });
//        log.info("🚀 [Startup] Initializing forex cache using /api/latest...");
//
//        this.forexService.getLatestForex()
//                .thenAccept(response -> log.info("✅ [Startup] Cached {} forex rates from latest API", response.rates().size()))
//                .exceptionally(ex -> {
//                    log.error("❌ [Startup] Failed to cache forex from latest API on startup: {}", ex.getMessage(), ex);
//                    return null;
//                });

        final BigDecimal calculatedAmount = this.forexService.calculateExchange("USD", BigDecimal.valueOf(3)).join();
        System.out.println("💱 Final exchange amount:" + calculatedAmount);

        final BigDecimal calculatedAmountTwo = this.forexService.calculateExchange("USD", "THB", BigDecimal.valueOf(3)).join();
        System.out.println("💱 Final exchange amount:" + calculatedAmountTwo);
    }
}
