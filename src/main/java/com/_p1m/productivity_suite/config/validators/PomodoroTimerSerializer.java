package com._p1m.productivity_suite.config.validators;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PomodoroTimerSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        long minutes = value / 60;
        long seconds = value % 60;
        gen.writeString(String.format("%02d:%02d", minutes, seconds));
    }
}

