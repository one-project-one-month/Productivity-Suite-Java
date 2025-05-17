package com._p1m.productivity_suite.features.currency.repo;

import com._p1m.productivity_suite.data.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency,Long> {
}
