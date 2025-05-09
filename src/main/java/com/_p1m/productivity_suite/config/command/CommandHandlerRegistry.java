package com._p1m.productivity_suite.config.command;

import com._p1m.productivity_suite.config.annotations.CommandType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandHandlerRegistry {

    private final Map<String, NewCommandSourceHandler> handlers = new HashMap<>();

    @Autowired
    public CommandHandlerRegistry(List<NewCommandSourceHandler> handlerList) {
        for (NewCommandSourceHandler handler : handlerList) {
            CommandType annotation = handler.getClass().getAnnotation(CommandType.class);
            if (annotation != null) {
                String key = annotation.entity() + ":" + annotation.action();
                handlers.put(key, handler);
            }
        }
    }

    public NewCommandSourceHandler getHandler(String entity, String action) {
        return handlers.get(entity + ":" + action);
    }
}
