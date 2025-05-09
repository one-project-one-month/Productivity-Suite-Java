package com._p1m.productivity_suite.config.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public record JsonCommand(String json, String entity, String action) {

    public <T> T parse(Class<T> clazz) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, clazz);
    }

    public static JsonCommand from(CommandWrapper wrapper) {
        return new JsonCommand(wrapper.json(), wrapper.entityName(), wrapper.actionName());
    }
}
