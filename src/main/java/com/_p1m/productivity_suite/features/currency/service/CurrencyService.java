package com._p1m.productivity_suite.features.currency.service;

import com._p1m.productivity_suite.data.models.Currency;
import com._p1m.productivity_suite.features.currency.dto.CurrencyRequest;
import com._p1m.productivity_suite.features.currency.repo.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface CurrencyService {

    void createCurrency(final String authHeader, final CurrencyRequest currencyRequest);
}
