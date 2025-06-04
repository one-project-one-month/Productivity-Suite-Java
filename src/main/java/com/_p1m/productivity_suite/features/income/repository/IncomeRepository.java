package com._p1m.productivity_suite.features.income.repository;

import com._p1m.productivity_suite.data.models.Income;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByUserId(Long userId, Sort sort);
}
