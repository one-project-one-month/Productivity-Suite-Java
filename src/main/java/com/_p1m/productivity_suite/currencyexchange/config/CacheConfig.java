package com._p1m.productivity_suite.currencyexchange.config;

import com._p1m.productivity_suite.currencyexchange.integration.ForexApiClient;
import com._p1m.productivity_suite.currencyexchange.response.ForexHistoryResponse;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    private final ForexApiClient apiClient;

    @Bean
    public AsyncLoadingCache<String, ForexHistoryResponse> asyncForexCache() {
        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(Duration.ofHours(12))
                .refreshAfterWrite(Duration.ofHours(6))
                .buildAsync((date, executor) ->
                        this.apiClient.fetchForexByDate(date)
                                .toFuture()
                );
    }
}
