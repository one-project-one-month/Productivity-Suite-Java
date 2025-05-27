package com._p1m.productivity_suite.currencyexchange.service;

import com._p1m.productivity_suite.currencyexchange.response.ForexHistoryResponse;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ForexService {
    CompletableFuture<ForexHistoryResponse> getForexByDate(final String date);
    CompletableFuture<Optional<String>> getForexByCurrency(final String date, final String currency);
    /*
    * try {
    *     final BigDecimal calculatedAmount = forexService.calculateExchange("USD", currency).get();
    *     System.out.println("💱 Final exchange amount: " + calculatedAmount);
    * } catch (Exception e) {
    *     e.printStackTrace();
    * }
    * or
    *  BigDecimal calculatedAmount = forexService.calculateExchange("USD", BigDecimal.valueOf(3)).join();
    *  System.out.println("💱 Final exchange amount: " + calculatedAmount);
    */
    CompletableFuture<BigDecimal> calculateExchange(final String currency, final BigDecimal amount);
}
