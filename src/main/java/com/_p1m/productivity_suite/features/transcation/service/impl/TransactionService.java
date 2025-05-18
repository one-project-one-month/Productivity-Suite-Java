package com._p1m.productivity_suite.features.transcation.service.impl;


import com._p1m.productivity_suite.features.transcation.dto.TransactionRequest;
import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;
import java.util.List;

public interface TransactionService {
    void createTransaction(final String authHeader, final TransactionRequest transactionRequest);
    List<TransactionResponse> retrieveAll (final String authHeader);
    TransactionResponse retrieveOne(final long id);
    void updateTransaction(final long id, final TransactionRequest transactionRequest);
    void deleteTransaction(final Long id);
    void updateStatus(final long id, final boolean active);

}
