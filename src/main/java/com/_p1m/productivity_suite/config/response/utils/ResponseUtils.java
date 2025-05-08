package com._p1m.productivity_suite.config.response.utils;

import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;

public class ResponseUtils {

    public static ResponseEntity<ApiResponse> buildResponse(final HttpServletRequest request, final ApiResponse response, final double requestTime) {
        final HttpStatus status = HttpStatus.valueOf(response.getCode());

        if (response.getMeta() == null) {
            final String method = request.getMethod();
            final String endpoint = request.getRequestURI();
            response.setMeta(new HashMap<>());
            response.getMeta().put("method", method);
            response.getMeta().put("endpoint", endpoint);
        }

        response.setDuration(Instant.now().getEpochSecond() - requestTime);
        return new ResponseEntity<>(response, status);
    }
}