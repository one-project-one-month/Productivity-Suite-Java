package com._p1m.productivity_suite.currencyexchange.service;

import com._p1m.productivity_suite.currencyexchange.response.ForexCurrencyResponse;
import com._p1m.productivity_suite.currencyexchange.response.ForexResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ForexService {
//    CompletableFuture<ForexHistoryResponse> getForexByDate(final String date);
    CompletableFuture<ForexResponse> getLatestForex();
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
    *  System.out.println("💱 Final exchange amount:" + calculatedAmount);
    */
    CompletableFuture<BigDecimal> calculateExchange(final String currency, final BigDecimal amount);

    CompletableFuture<BigDecimal> calculateExchange(final String fromCurrency, final String toCurrency, final BigDecimal amount);

    CompletableFuture<List<ForexCurrencyResponse>> getCurrencyCodeList();

    List<ForexCurrencyResponse> getCurrencyList();
}
