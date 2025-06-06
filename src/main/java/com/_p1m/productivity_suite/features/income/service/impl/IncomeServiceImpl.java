package com._p1m.productivity_suite.features.income.service.impl;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.Income;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.income.dto.IncomeRequest;
import com._p1m.productivity_suite.features.income.dto.IncomeResponse;
import com._p1m.productivity_suite.features.income.repository.IncomeRepository;
import com._p1m.productivity_suite.features.income.service.IncomeService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void createIncome(final String authHeader, final IncomeRequest incomeRequest) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");
        final Category category = RepositoryUtils.findByIdOrThrow(this.categoryRepository, incomeRequest.categoryId(), "Category");

        final Income income = new Income(
            incomeRequest.amount(),
            category,
            user
        );
        PersistenceUtils.save(this.incomeRepository, income, "Income");
    }

    @Override
    public List<IncomeResponse> retrieveAll(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final List<Income> incomes = RepositoryUtils.findAllByUserId(
                userDto.getId(),
                Sort.by(Sort.Direction.ASC, "createdAt"),
                this.incomeRepository::findAllByUserId
        );
        return incomes.stream()
                .map(this::toIncomeResponse)
                .toList();
    }

    @Override
    public IncomeResponse retrieveOne(final Long id) {
        final Income income = RepositoryUtils.findByIdOrThrow(this.incomeRepository, id, "Income");
        return this.toIncomeResponse(income);
    }

    @Override
    public void updateIncome(final Long id, final IncomeRequest incomeRequest) {
        final Income income = RepositoryUtils.findByIdOrThrow(this.incomeRepository, id, "Income");

        income.setAmount(incomeRequest.amount());
        if (!income.getCategory().getId().equals(incomeRequest.categoryId())) {
            income.setCategory(RepositoryUtils.findByIdOrThrow(this.categoryRepository, incomeRequest.categoryId(), "Category"));
        }

        PersistenceUtils.save(this.incomeRepository, income, "Income");
    }

    @Override
    public void deleteIncome(final Long id) {
        final Income income = RepositoryUtils.findByIdOrThrow(this.incomeRepository, id, "Income");
        PersistenceUtils.deleteById(this.incomeRepository, id, "Income");
    }

    private IncomeResponse toIncomeResponse(final Income income) {
        return new IncomeResponse(
                income.getId(),
                income.getCategory().getId(),
                income.getCategory().getName(),
                income.getCategory().getDescription()
        );
    }
}
