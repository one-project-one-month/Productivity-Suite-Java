package com._p1m.productivity_suite.config.command;

import lombok.Getter;

@Getter
public class CommandProcessingResult {
    private final boolean success;
    private final Long resourceId;
    private final String message;
    private final Object data;

    private CommandProcessingResult(final boolean success, final Long resourceId, final String message, final Object data) {
        this.success = success;
        this.resourceId = resourceId;
        this.message = message;
        this.data = data;
    }

    public static CommandProcessingResult success(final Long id, final String message, final Object data) {
        return new CommandProcessingResult(true, id, message, data);
    }

    public static CommandProcessingResult failure(final String message) {
        return new CommandProcessingResult(false, null, message, false);
    }
}

