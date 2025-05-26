package com._p1m.productivity_suite.currencyexchange.service.impl;

import com._p1m.productivity_suite.currencyexchange.response.ForexHistoryResponse;
import com._p1m.productivity_suite.currencyexchange.service.ForexService;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForexServiceImpl implements ForexService {

    private final AsyncLoadingCache<String, ForexHistoryResponse> asyncForexCache;

    @Override
    public CompletableFuture<ForexHistoryResponse> getForexByDate(final String date) {
        log.debug("📦 [ForexServiceImpl] Fetching forex data for date: {}", date);
        return this.asyncForexCache.get(date);
    }

    @Override
    public CompletableFuture<Optional<String>> getForexByCurrency(final String date, final String currency) {
        return this.getForexByDate(date)
                .thenApply(response ->
                        Optional.ofNullable(response)
                                .map(ForexHistoryResponse::rates)
                                .map(rates -> rates.get(currency.toUpperCase()))
                )
                .thenApply(optionalRate -> {
                    optionalRate.ifPresentOrElse(
                            rate -> log.info("💱 [ForexServiceImpl] Found rate {} for currency={} on date={}", rate, currency, date),
                            () -> log.warn("⚠️ [ForexServiceImpl] No rate found for currency={} on date={}", currency, date)
                    );
                    return optionalRate;
                })
                .exceptionally(ex -> {
                    log.error("❌ [ForexServiceImpl] Error retrieving forex rate for {} on {}: {}", currency, date, ex.getMessage(), ex);
                    return Optional.empty();
                });
    }

    @Override
    public CompletableFuture<BigDecimal> calculateExchange(final String currency, final BigDecimal amount) {
        final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        log.debug("🔢 [ForexServiceImpl] Calculating exchange for {} {} using date {}", amount, currency, today);

        return this.getForexByCurrency(today, currency)
                .thenApply(optionalRate -> optionalRate
                        .map(rateStr -> {
                            try {
                                final BigDecimal rate = new BigDecimal(rateStr);
                                final BigDecimal exchanged = amount.multiply(rate);
                                log.info("✅ [ForexServiceImpl] {} {} = {} MMK at rate {}", amount, currency, exchanged, rate);
                                return exchanged;
                            } catch (final NumberFormatException e) {
                                log.error("❌ [ForexServiceImpl] Invalid rate format '{}' for currency {}", rateStr, currency);
                                throw new IllegalArgumentException("Invalid forex rate format for currency: " + currency);
                            }
                        })
                        .orElseThrow(() -> {
                            log.warn("⚠️ [ForexServiceImpl] No exchange rate found for currency={} on {}", currency, today);
                            return new IllegalStateException("No exchange rate found for currency: " + currency);
                        })
                );
    }
}
