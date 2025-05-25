package com._p1m.productivity_suite.features.currency.service.impl;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Currency;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.currency.dto.CurrencyRequest;
import com._p1m.productivity_suite.features.currency.dto.CurrencyResponse;
import com._p1m.productivity_suite.features.currency.repo.CurrencyRepository;
import com._p1m.productivity_suite.features.currency.service.CurrencyService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createCurrency(final String authHeader, final CurrencyRequest currencyRequest) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");

        final Currency currency = new Currency(
                currencyRequest.name(),
                user
        );

        PersistenceUtils.save(this.currencyRepository, currency, "Currency");
    }

    @Override
    public List<CurrencyResponse> retrieveAll(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final List<Currency> currencies = RepositoryUtils.findAllByUserId(
                userDto.getId(),
                Sort.by(Sort.Direction.ASC, "name"),
                this.currencyRepository::findAllByUserId
        );
        return currencies.stream()
                .map(this::toCurrencyResponse)
                .toList();
    }

    @Override
    public CurrencyResponse retrieveOne(final Long id) {
        final Currency currency = RepositoryUtils.findByIdOrThrow(this.currencyRepository, id, "Currency");
        return this.toCurrencyResponse(currency);
    }

    private CurrencyResponse toCurrencyResponse(final Currency currency) {
        return new CurrencyResponse(
                currency.getId(),
                currency.getName(),
                currency.getCreatedAt(),
                currency.getUpdatedAt()
        );
    }

    @Override
    public void updateCurrency(final Long id, final CurrencyRequest currencyRequest) {
        final Currency currency = RepositoryUtils.findByIdOrThrow(this.currencyRepository,id,"Currency");

        currency.setName(currencyRequest.name());
        PersistenceUtils.save(this.currencyRepository, currency,"Currency");
    }

    @Override
    public void deleteCurrency(final Long id) {
        final Currency currency = RepositoryUtils.findByIdOrThrow(this.currencyRepository,id,"Currency");
        PersistenceUtils.deleteById(this.currencyRepository, id, "Currency");
    }
}
