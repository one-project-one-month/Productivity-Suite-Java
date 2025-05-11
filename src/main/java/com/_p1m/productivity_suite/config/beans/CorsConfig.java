package com._p1m.productivity_suite.config.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${application.frontend-urls}")
    private String frontendUrlsCsv;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final List<String> allowedFrontendUrls = parseFrontendUrls(frontendUrlsCsv);
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedFrontendUrls);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private List<String> parseFrontendUrls(String csv) {
        return List.of(csv.split("\\s*,\\s*"));
    }
}