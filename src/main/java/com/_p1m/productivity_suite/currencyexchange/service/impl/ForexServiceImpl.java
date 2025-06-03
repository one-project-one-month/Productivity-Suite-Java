package com._p1m.productivity_suite.currencyexchange.service.impl;

import com._p1m.productivity_suite.config.utils.ValidationUtils;
import com._p1m.productivity_suite.currencyexchange.integration.ForexApiClient;
import com._p1m.productivity_suite.currencyexchange.response.ForexCurrencyResponse;
import com._p1m.productivity_suite.currencyexchange.response.ForexResponse;
import com._p1m.productivity_suite.currencyexchange.service.ForexService;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForexServiceImpl implements ForexService {

    private final AsyncLoadingCache<String, ForexResponse> asyncForexCache;
    private final ForexApiClient apiClient;

//    @Override
//    public CompletableFuture<ForexHistoryResponse> getForexByDate(final String date) {
//        log.debug("📦 [ForexServiceImpl] Fetching forex data for date: {}", date);
//        return this.asyncForexCache.get(date);
//    }

    @Override
    public CompletableFuture<ForexResponse> getLatestForex() {
        log.info("📦 [ForexServiceImpl] Getting latest forex data from cache...");
        return asyncForexCache.get("latest");
    }

    @Override
    public CompletableFuture<Optional<String>> getForexByCurrency(final String date, final String currency) {
        return this.getLatestForex()
                .thenApply(response ->
                        Optional.ofNullable(response)
                                .map(ForexResponse::rates)
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

    @Override
    public CompletableFuture<BigDecimal> calculateExchange(final String fromCurrency, final String toCurrency, final BigDecimal amount) {

        ValidationUtils.requireNonNull(fromCurrency, "fromCurrency");
        ValidationUtils.requireNonNull(toCurrency, "toCurrency");
        ValidationUtils.requireNonNull(amount, "amount");

        final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        log.debug("🔄 [ForexServiceImpl] Converting {} {} to {}", amount, fromCurrency, toCurrency);

        final CompletableFuture<Optional<String>> fromRateFuture = getForexByCurrency(today, fromCurrency);
        final CompletableFuture<Optional<String>> toRateFuture = getForexByCurrency(today, toCurrency);

        return fromRateFuture.thenCombine(toRateFuture, (fromRateOpt, toRateOpt) -> {
            if (fromRateOpt.isEmpty() || toRateOpt.isEmpty()) {
                final String missing = fromRateOpt.isEmpty() ? fromCurrency : toCurrency;
                log.warn("⚠️ [ForexServiceImpl] Missing rate for currency={}", missing);
                throw new IllegalStateException("No exchange rate found for currency: " + missing);
            }

            try {
                final BigDecimal fromRate = new BigDecimal(fromRateOpt.get());
                final BigDecimal toRate = new BigDecimal(toRateOpt.get());

                final BigDecimal exchanged = amount
                        .multiply(fromRate)
                        .divide(toRate, 4, RoundingMode.HALF_UP);

                log.info("✅ [ForexServiceImpl] {} {} = {} {} (fromRate={}, toRate={})",
                        amount, fromCurrency, exchanged, toCurrency, fromRate, toRate);

                return exchanged;
            } catch (final NumberFormatException e) {
                log.error("❌ [ForexServiceImpl] Invalid rate format (from={} or to={})", fromRateOpt.get(), toRateOpt.get(), e);
                throw new IllegalArgumentException("Invalid rate format for currency");
            }
        }).exceptionally(ex -> {
            log.error("💥 [ForexServiceImpl] Exchange calculation failed: {}", ex.getMessage(), ex);
            throw new RuntimeException("Exchange calculation failed", ex);
        });
    }

    @Override
    public CompletableFuture<List<ForexCurrencyResponse>> getCurrencyCodeList() {
        return getLatestForex()
                .thenApply(response -> {
                    List<ForexCurrencyResponse> currencies = new ArrayList<>();
                    long id = 1L;
                    currencies.add(new ForexCurrencyResponse(id++, "MMK"));
                    for (String name : response.rates().keySet()) {
                        currencies.add(new ForexCurrencyResponse(id++, name));
                    }
                    return currencies;
                });
    }

    @Override
    public List<ForexCurrencyResponse> getCurrencyList() {
        return this.getCurrencyCodeList().join();
    }
}
