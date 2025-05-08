package com._p1m.productivity_suite.config.request;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

/**
 * Utility class for handling HTTP request-related operations.
 * Provides common methods to extract and validate request metadata.
 */
public final class RequestUtils {

    private static final String REQUEST_START_TIME_HEADER = "X-Request-Start-Time";

    private RequestUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Extracts the request start time from the HTTP request header.
     * If the header is missing or invalid, it falls back to the current time.
     *
     * @param request the HTTP servlet request containing the headers.
     * @return the request start time as a double.
     */
    public static double extractRequestStartTime(HttpServletRequest request) {
        String headerValue = request.getHeader(REQUEST_START_TIME_HEADER);

        if (headerValue == null) {
            logFallback("Request start time header is missing. Using current time.");
            return Instant.now().getEpochSecond();
        }

        try {
            return Double.parseDouble(headerValue);
        } catch (NumberFormatException ex) {
            logFallback("Invalid format for request start time header: " + headerValue);
            return Instant.now().getEpochSecond();
        }
    }

    /**
     * Logs a warning message for fallback scenarios.
     * Can be integrated with a logging framework like SLF4J or Logback.
     *
     * @param message the warning message to log.
     */
    private static void logFallback(String message) {
        System.err.println(message); // Later Integrate with SLF4J
    }
}
