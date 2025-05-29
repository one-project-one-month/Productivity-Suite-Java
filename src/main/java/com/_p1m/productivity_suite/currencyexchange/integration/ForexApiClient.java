package com._p1m.productivity_suite.currencyexchange.integration;

import com._p1m.productivity_suite.currencyexchange.response.ForexResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForexApiClient {

//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final WebClient forexWebClient;

//    public Mono<ForexHistoryResponse> fetchForexByDate(final String targetDate) {
//        final String actualApiDate = LocalDate.parse(targetDate, FORMATTER).minusDays(1).format(FORMATTER);
//        log.info("🌐 [ForexApiClient] Calling CBM API for target={} (actual request date={})", targetDate, actualApiDate);
//
//        return this.forexWebClient.get()
//                .uri("/api/history/{date}", actualApiDate)
//                .retrieve()
//                .bodyToMono(ForexHistoryResponse.class)
//                .doOnSuccess(res -> Optional.ofNullable(res).ifPresentOrElse(
//                        r -> log.info("✅ [ForexApiClient] Received response for {} with {} rates", actualApiDate, r.rates().size()),
//                        () -> log.warn("⚠️ [ForexApiClient] Empty response for {}", actualApiDate)
//                ))
//                .doOnError(err -> log.error("❌ [ForexApiClient] Error fetching data for {}: {}", actualApiDate, err.getMessage(), err));
//    }

    public Mono<ForexResponse> fetchLatestForex() {
        log.info("🌐 [ForexApiClient] Calling CBM API for latest forex data...");

        return this.forexWebClient.get()
                .uri("/api/latest")
                .retrieve()
                .bodyToMono(ForexResponse.class)
                .doOnSuccess(res -> Optional.ofNullable(res).ifPresentOrElse(
                        r -> log.info("✅ [ForexApiClient] Received latest forex data with {} rates", r.rates().size()),
                        () -> log.warn("⚠️ [ForexApiClient] Empty response from latest forex API")
                ))
                .doOnError(err -> log.error("❌ [ForexApiClient] Error fetching latest forex data: {}", err.getMessage(), err));
    }
}
