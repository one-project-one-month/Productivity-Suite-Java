package com._p1m.productivity_suite.features.expensesummary.service.impl;

import com._p1m.productivity_suite.currencyexchange.response.ForexCurrencyResponse;
import com._p1m.productivity_suite.currencyexchange.service.ForexService;
import com._p1m.productivity_suite.data.enums.CategoryType;
import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;
import com._p1m.productivity_suite.features.categories.repository.jdbc.CategoryJdbcRepository;
import com._p1m.productivity_suite.features.expensesummary.dto.CategoryAndCurrencyResponse;
import com._p1m.productivity_suite.features.expensesummary.dto.CategoryDataForExpenseSummary;
import com._p1m.productivity_suite.features.expensesummary.dto.CurrencyDataForExpenseSummary;
import com._p1m.productivity_suite.features.expensesummary.repository.ExpenseSummaryRepository;
import com._p1m.productivity_suite.features.expensesummary.service.ExpenseSummaryService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ExpenseSummaryServiceImpl implements ExpenseSummaryService {

    private final CategoryJdbcRepository categoryRepository;
    private final ForexService forexService;
    private final UserUtil userUtil;
    private final ExpenseSummaryRepository expenseSummaryRepository;

    public ExpenseSummaryServiceImpl(final CategoryJdbcRepository categoryRepository, final ForexService forexService, final UserUtil userUtil,final ExpenseSummaryRepository expenseSummaryRepository) {
        this.categoryRepository = categoryRepository;
        this.forexService = forexService;
        this.userUtil = userUtil;
        this.expenseSummaryRepository = expenseSummaryRepository;
    }

    @Override
    public CategoryAndCurrencyResponse retrieveCategoryAndCurrency(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final List<CategoryResponse> categories = this.categoryRepository.findByUserIdAndType(userDto.getId(), CategoryType.BUDGET_TRACKER.getValue());

        final List<CategoryDataForExpenseSummary> categoryDtoList = categories.stream()
                .map(category -> new CategoryDataForExpenseSummary(
                        category.name().toLowerCase().replace(" ", ""),
                        category.name(),
                        category.description()
                ))
                .toList();

        final List<ForexCurrencyResponse> forexCurrencyResponseList = this.forexService.getCurrencyList();

        final List<CurrencyDataForExpenseSummary> currencyDtoList = forexCurrencyResponseList.stream()
                .map(currency -> new CurrencyDataForExpenseSummary(
                        currency.id(),
                        currency.name()
                ))
                .toList();

        return new CategoryAndCurrencyResponse(categoryDtoList, currencyDtoList);
    }

    @Override
    public List<Map<String, Object>> getDailyFlatSummary(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        return this.expenseSummaryRepository.findDailySummaryByUser(userDto.getId());
    }

    @Override
    public List<Map<String, Object>> getConvertedCategorySummaryByDay(final String authHeader, final Long categoryId, final String toCurrency) {
        final UserDto user = this.userUtil.getCurrentUserDto(authHeader);

        final List<Map<String, Object>> rawData = expenseSummaryRepository.findByUserIdAndCategoryId(user.getId(), categoryId);

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yy");

        final List<CompletableFuture<Map<String, Object>>> futures = rawData.stream()
                .map(entry -> {
                    final long timestamp = ((Number) entry.get("transaction_date")).longValue();
                    final String category = (String) entry.get("category");
                    final BigDecimal amount = (BigDecimal) entry.get("amount");

                    final LocalDate date = Instant.ofEpochSecond(timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    final String formattedDate = date.format(formatter);

                    return this.forexService.calculateExchange(user.getCurrencyCode(), toCurrency, amount)
                            .thenApply(converted -> {
                                Map<String, Object> map = new LinkedHashMap<>();
                                map.put("date", formattedDate);
                                map.put(category, converted);
                                return map;
                            });
                }).toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}
