package com._p1m.productivity_suite.config.utils;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import java.util.Optional;
import java.util.function.BiFunction;

public class AuthorizationUtils {

    /**
     * Generic method to check if the user is authorized to access the entity by comparing their IDs.
     *
     * @param userDto            The current user data
     * @param entity             The entity to check authorization against
     * @param userIdExtractor    A function that extracts the user ID from the entity
     * @param <T>                The type of the entity
     */
    public static <T> void checkUserAuthorization(UserDto userDto, T entity, BiFunction<T, UserDto, Long> userIdExtractor) {
        Optional.ofNullable(userDto.getId())
                .filter(userId -> userId.equals(userIdExtractor.apply(entity, userDto)))
                .orElseThrow(() -> new UnauthorizedException("You are not authorized to update this entity."));
    }
}
