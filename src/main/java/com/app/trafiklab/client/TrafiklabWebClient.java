package com.app.trafiklab.client;

import com.app.trafiklab.config.WebClientConfig;
import com.app.trafiklab.exception.ExternalApiException;
import com.app.trafiklab.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class TrafiklabWebClient {
    private static final Logger logger = LoggerFactory.getLogger(TrafiklabWebClient.class);
    private static final String JOUR_URL_TEMPLATE = "%s?model=jour&DefaultTransportModeCode=BUS&key=%s";
    private static final String STOP_URL_TEMPLATE = "%s?model=stop&DefaultTransportModeCode=BUS&key=%s";

    private final WebClient webClient;
    private final String baseUrl;
    private final String apiKey;

    public TrafiklabWebClient(@Value("${trafiklab.base.url}") String baseUrl,
                              @Value("${trafiklab.api.key}") String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.webClient = new WebClientConfig().webClient();
    }

    public List<LineData> getAllBusLinesByJourModel() {
        logger.info("Function getAllBusLinesByJourModel() is invoked");
        BusLineResponse busLineResponse = callToTrafiklabAPI(JOUR_URL_TEMPLATE.formatted(baseUrl, apiKey),
                BusLineResponse.class);
        return Optional.ofNullable(busLineResponse)
                .map(BusLineResponse::getResponseData)
                .map(BusLineResponseData::getResult)
                .orElseThrow(() -> new ExternalApiException("Error calling external API: " + Objects.requireNonNull(busLineResponse).getMessage()));
    }

    public List<StopPoint> getAllBusLinesByStopPointModel() {
        logger.info("Function getAllStopPoints() is invoked");
        BusStopResponse busStopResponse = callToTrafiklabAPI(STOP_URL_TEMPLATE.formatted(baseUrl, apiKey),
                BusStopResponse.class);
        return Optional.ofNullable(busStopResponse)
                .map(BusStopResponse::getResponseData)
                .map(BusStopResponseData::getResult)
                .orElseThrow(() -> new ExternalApiException("Error calling external API: " + Objects.requireNonNull(busStopResponse).getMessage()));
    }

    public <T> T callToTrafiklabAPI(String uri, Class<T> clazz) {
        return webClient.get()
                .uri(uri)
                .header("Accept-Encoding", "gzip")
                .retrieve()
                .bodyToMono(clazz).block();
    }
}
