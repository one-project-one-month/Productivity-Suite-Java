package com._p1m.productivity_suite.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String json = """
        {
            "success": 0,
            "code": 401,
            "message": "Unauthorized",
            "data": "You are not authorized to access this resource.",
            "meta": {
                "method": "%s",
                "endpoint": "%s"
            },
            "duration": %d
        }
        """.formatted(
                request.getMethod(),
                request.getRequestURI(),
                Instant.now().getEpochSecond()
        );

        response.getWriter().write(json);
    }
}