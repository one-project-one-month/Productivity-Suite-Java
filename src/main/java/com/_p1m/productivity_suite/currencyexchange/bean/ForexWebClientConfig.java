package com._p1m.productivity_suite.currencyexchange.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Configuration
public class ForexWebClientConfig {

    @Value("${application.central-bank-url}")
    private String centralBankBaseUrl;

    @Bean
    public WebClient forexWebClient(final Builder builder) {
        return builder
                .baseUrl(centralBankBaseUrl)
                .build();
    }
}
