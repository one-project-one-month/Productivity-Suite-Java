package com._p1m.productivity_suite.config.validators;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class PomodoroTimerDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken token = parser.currentToken();

        if (token == JsonToken.VALUE_STRING) {
            String timeString = parser.getText().trim();

            if (!timeString.matches("\\d{1,2}:\\d{2}")) {
                throw new IllegalArgumentException("Invalid time format. Expected MM:ss");
            }

            String[] parts = timeString.split(":");
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);

            return (minutes * 60L + seconds);

        } else if (token == JsonToken.VALUE_NUMBER_INT) {
            return parser.getLongValue(); 
        }

        throw new IllegalArgumentException("Invalid input for remainingTime: expected MM:ss or numeric seconds.");
    }
}
