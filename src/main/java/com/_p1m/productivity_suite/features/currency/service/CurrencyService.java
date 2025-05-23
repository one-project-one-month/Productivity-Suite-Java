package com._p1m.productivity_suite.features.currency.service;

import com._p1m.productivity_suite.features.currency.dto.CurrencyRequest;
import com._p1m.productivity_suite.features.currency.dto.CurrencyResponse;

import java.util.List;

public interface CurrencyService {
    void createCurrency(final String authHeader, final CurrencyRequest currencyRequest);
    List<CurrencyResponse> retrieveAll(final String authHeader);
    CurrencyResponse retrieveOne(final Long id);

    void updateCurrency(final Long id, final CurrencyRequest currencyRequest);
    void deleteCurrency(final Long id);
}
