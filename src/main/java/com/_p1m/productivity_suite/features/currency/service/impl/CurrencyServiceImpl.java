package com._p1m.productivity_suite.features.currency.service.impl;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Currency;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.currency.dto.CurrencyRequest;
import com._p1m.productivity_suite.features.currency.repo.CurrencyRepo;
import com._p1m.productivity_suite.features.currency.service.CurrencyService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepo currencyRepo;
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createCurrency(final String authHeader,final CurrencyRequest currencyRequest) {
        UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        User user = RepositoryUtils.findByIdOrThrow(userRepository, userDto.getId(), "User");

        Currency currency = new Currency(
                currencyRequest.name(),
                currencyRequest.active(),
                List.of(user)

        );
        PersistenceUtils.save(currencyRepo, currency, "Currency");
    }
}
