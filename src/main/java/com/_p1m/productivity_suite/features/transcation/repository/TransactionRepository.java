package com._p1m.productivity_suite.features.transcation.repository;

import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByUserId(Long userId,Sort sort);
}
