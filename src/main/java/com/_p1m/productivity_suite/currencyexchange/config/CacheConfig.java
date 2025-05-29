package com._p1m.productivity_suite.currencyexchange.config;

import com._p1m.productivity_suite.currencyexchange.integration.ForexApiClient;
import com._p1m.productivity_suite.currencyexchange.response.ForexResponse;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CacheConfig {

    private final ForexApiClient apiClient;

    @Bean
    public AsyncLoadingCache<String, ForexResponse> asyncForexCache() {
        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(Duration.ofHours(24))   // Cache TTL
                .refreshAfterWrite(Duration.ofHours(6))   // Background refresh
                .buildAsync((key, executor) -> {
                    log.debug("🔁 [CacheLoader] Loading forex data for key={}", key);
                    return this.apiClient.fetchLatestForex().toFuture();
                });
    }
}
