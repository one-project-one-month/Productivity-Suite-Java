package com._p1m.productivity_suite.features.transcation.service.impl;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.Transaction;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.transcation.dto.TransactionRequest;
import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;
import com._p1m.productivity_suite.features.transcation.repository.TransactionRepository;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public void createTransaction(final String authHeader, final TransactionRequest transactionRequest) {
       
            final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
            final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");

            final Category category = RepositoryUtils.findByIdOrThrow(this.categoryRepository, transactionRequest.categoryId(), "Category");

            final Transaction transaction = new Transaction(
                    transactionRequest.amount(),
                    transactionRequest.description(),
                    transactionRequest.transactionDate(),
                    user,
                    category
            );

            PersistenceUtils.save(this.transactionRepository, transaction, "Transaction");

    }
    @Override
    public List<TransactionResponse> retrieveAll(String authHeader) {

        final  UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Sort sortByCreatedAt = Sort.by(Sort.Direction.ASC,"createdAt");

        final List<Transaction> transaction = RepositoryUtils.findAllByUserId(userDto.getId(),sortByCreatedAt,this.transactionRepository::findAllByUserId);
        return transaction.stream()
                .map(this::toTransactionResponseWithType)
                .toList();

    }


    private TransactionResponse toTransactionResponseWithType(final Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

    @Override
    public TransactionResponse retrieveOne(long id) {
        final Transaction transaction = RepositoryUtils.findByIdOrThrow(this.transactionRepository,id,"Transaction");
        return this.toTransactionResponseWithType(transaction);
    }

    @Override
    public void updateTransaction(final long id, final TransactionRequest transactionRequest) {
    final Transaction transaction = RepositoryUtils.findByIdOrThrow(this.transactionRepository, id, "Transaction");
    transaction.setDescription(transactionRequest.description());
    transaction.setTransactionDate(transactionRequest.transactionDate());
    transaction.setAmount(transactionRequest.amount());
    PersistenceUtils.save(this.transactionRepository, transaction, "Transaction");
    }

    @Override
    public void deleteTransaction(final Long id) {
        RepositoryUtils.findByIdOrThrow(this.transactionRepository,id,"Transaction");
        PersistenceUtils.deleteById(this.transactionRepository,id,"Transaction");

    }

}
