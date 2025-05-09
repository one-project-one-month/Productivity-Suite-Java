package com._p1m.productivity_suite.config.command;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NewCommandSourceHandler {
    CommandProcessingResult processCommand(JsonCommand command) throws JsonProcessingException;
}
