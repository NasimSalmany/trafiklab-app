package com.app.trafiklab;

import com.app.trafiklab.client.TrafiklabWebClient;
import com.app.trafiklab.exception.ExternalApiException;
import com.app.trafiklab.model.LineData;
import com.app.trafiklab.model.StopPoint;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@ExtendWith(SpringExtension.class)
public class TrafiklabWebClientTest {
    private TrafiklabWebClient trafiklabWebClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        this.trafiklabWebClient = new TrafiklabWebClient(mockWebServer.url("/").uri().toString(), "");
    }
    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }
    @Test
    public void getAllBusLinesShouldReturnBuslines() throws IOException {
        MockResponse mockResponseJur = new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\n" +
                        "  \"StatusCode\": 0,\n" +
                        "  \"Message\": null,\n" +
                        "  \"ExecutionTime\": 582,\n" +
                        "  \"ResponseData\": {\n" +
                        "    \"Version\": \"2023-03-30 00:10\",\n" +
                        "    \"Type\": \"JourneyPatternPointOnLine\",\n" +
                        "    \"Result\": [\n" +
                        "      {\n" +
                        "        \"LineNumber\": \"1\",\n" +
                        "        \"DirectionCode\": \"1\",\n" +
                        "        \"JourneyPatternPointNumber\": \"10008\",\n" +
                        "        \"LastModifiedUtcDateTime\": \"2022-02-15 00:00:00.000\",\n" +
                        "        \"ExistsFromDate\": \"2022-02-15 00:00:00.000\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}");
        mockWebServer.enqueue(mockResponseJur);

        MockResponse mockResponseStop = new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\n" +
                        "  \"StatusCode\": 0,\n" +
                        "  \"Message\": null,\n" +
                        "  \"ExecutionTime\": 656,\n" +
                        "  \"ResponseData\": {\n" +
                        "    \"Version\": \"2023-03-30 00:10\",\n" +
                        "    \"Type\": \"StopPoint\",\n" +
                        "    \"Result\": [\n" +
                        "      {\n" +
                        "        \"StopPointNumber\": \"10001\",\n" +
                        "        \"StopPointName\": \"Stadshagsplan\",\n" +
                        "        \"StopAreaNumber\": \"10001\",\n" +
                        "        \"LocationNorthingCoordinate\": \"59.3373571967995\",\n" +
                        "        \"LocationEastingCoordinate\": \"18.0214674159693\",\n" +
                        "        \"ZoneShortName\": \"A\",\n" +
                        "        \"StopAreaTypeCode\": \"BUSTERM\",\n" +
                        "        \"LastModifiedUtcDateTime\": \"2022-10-28 00:00:00.000\",\n" +
                        "        \"ExistsFromDate\": \"2022-10-28 00:00:00.000\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}");
        mockWebServer.enqueue(mockResponseStop);

        List<LineData> responseLines = trafiklabWebClient.getAllBusLinesByJourModel();
        List<StopPoint> responseStops = trafiklabWebClient.getAllBusLinesByStopPointModel();

        assertThat(responseLines.size()).isEqualTo(1);
        assertThat(responseLines.get(0).getLineNumber()).isEqualTo(1);
        assertThat(responseStops.size()).isEqualTo(1);
        assertThat(responseStops.get(0).getStopPointNumber()).isEqualTo(10001);
    }

    @Test
    public void getAllBusLinesMoreThanSpecifRateShouldReturnProperError() throws IOException {
        MockResponse mockResponseJur = new MockResponse()
                .setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\n" +
                        "    \"StatusCode\": 1006,\n" +
                        "    \"Message\": \"Too many requests per minute\"\n" +
                        "}");
        mockWebServer.enqueue(mockResponseJur);
        Throwable thrownException = catchThrowable(() -> trafiklabWebClient.getAllBusLinesByJourModel());

        assertThat(thrownException).isInstanceOf(ExternalApiException.class)
                .hasMessageContaining("Error calling external API: Too many requests per minute");
    }
}
