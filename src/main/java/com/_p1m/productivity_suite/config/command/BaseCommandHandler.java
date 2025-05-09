package com._p1m.productivity_suite.config.command;

import com._p1m.productivity_suite.config.exceptions.BeanValidationException;
import com._p1m.productivity_suite.config.exceptions.UnsupportedParameterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseCommandHandler implements NewCommandSourceHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Validator validator;

    static {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    protected <T> void validateSupportedParametersAndBean(final JsonCommand command, final Class<T> dtoClass) throws JsonProcessingException {

        final Set<String> supportedFields = Arrays.stream(dtoClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());

        final Map<String, Object> commandMap = objectMapper.readValue(command.json(), new TypeReference<>() {});

        final Set<String> unsupportedKeys = commandMap.keySet().stream()
                .filter(key -> !supportedFields.contains(key))
                .collect(Collectors.toSet());

        if (!unsupportedKeys.isEmpty()) {
            throw new UnsupportedParameterException("Unsupported parameter(s): " + String.join(", ", unsupportedKeys));
        }

        final T dto = objectMapper.readValue(command.json(), dtoClass);

        final Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            throw new BeanValidationException(violations);
        }
    }
}
