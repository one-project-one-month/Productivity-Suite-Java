package com._p1m.productivity_suite.config.command;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CommandProcessingService {
    CommandProcessingResult process(final JsonCommand command) throws JsonProcessingException;
}
