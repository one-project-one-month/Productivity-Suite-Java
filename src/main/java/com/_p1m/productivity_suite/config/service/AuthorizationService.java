package com._p1m.productivity_suite.config.service;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AuthorizationService {

    public <T> void authorize(UserDto userDto, T entity, BiFunction<T, UserDto, Long> userIdExtractor) {
        final Long ownerId = userIdExtractor.apply(entity, userDto);
        if (!userDto.getId().equals(ownerId)) {
            throw new UnauthorizedException("You are not authorized to access this resource.");
        }
    }
}
