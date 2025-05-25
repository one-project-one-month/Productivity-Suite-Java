package com._p1m.productivity_suite.features.currency.repo;

import com._p1m.productivity_suite.data.models.Currency;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findAllByUserId(Long userId, Sort sort);
}
