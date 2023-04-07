package com.app.trafiklab;

import com.app.trafiklab.client.TrafiklabWebClient;
import com.app.trafiklab.model.BusLine;
import com.app.trafiklab.model.BusLineResponseData;
import com.app.trafiklab.model.BusStopResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrafiklabAppIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TrafiklabWebClient trafiklabWebClient;

    @Test
    void whenGeTBusLinesWithMostBusStopsThenReturnCorrectResult() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BusLineResponseData jurResponse = objectMapper.readValue(new File("src/test/java/resources/data/jourResponse.json"), BusLineResponseData.class);
        BusStopResponseData stopPointResponse = objectMapper.readValue(new File("src/test/java/resources/data/stopPointResponse.json"), BusStopResponseData.class);

        given(trafiklabWebClient.getAllBusLinesByJourModel()).willReturn(jurResponse.getResult());
        given(trafiklabWebClient.getAllBusLinesByStopPointModel()).willReturn(stopPointResponse.getResult());
        webTestClient.get().uri("/buslines/most-stops")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(BusLine.class).value(busLines -> {
                    assertThat(busLines.stream().filter(line -> line.getLineNumber().equals(1)).findAny()).isNotEmpty();
                    assertThat(busLines.size()).isEqualTo(10);
                });
    }
}
