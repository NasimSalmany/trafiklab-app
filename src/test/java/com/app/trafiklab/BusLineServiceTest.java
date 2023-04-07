package com.app.trafiklab;

import com.app.trafiklab.client.TrafiklabWebClient;
import com.app.trafiklab.model.BusLine;
import com.app.trafiklab.model.BusLineResponseData;
import com.app.trafiklab.model.BusStopResponseData;
import com.app.trafiklab.service.BusLineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BusLineServiceTest {

    @InjectMocks
    private BusLineService busLineService;
    @Mock
    private TrafiklabWebClient trafiklabAPIClient;

    @Test
    public void whenGetBuslinesWithMostStopsThenShouldReturnTop10Result() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BusLineResponseData jurResponse = objectMapper.readValue(new File("src/test/java/resources/data/jourResponse.json"), BusLineResponseData.class);
        BusStopResponseData stopPointResponse = objectMapper.readValue(new File("src/test/java/resources/data/stopPointResponse.json"), BusStopResponseData.class);

        when(trafiklabAPIClient.getAllBusLinesByJourModel()).thenReturn(jurResponse.getResult());
        when(trafiklabAPIClient.getAllBusLinesByStopPointModel()).thenReturn(stopPointResponse.getResult());

        List<BusLine> busLinesWithMostStops = busLineService.getBusLinesWithMostStops();
        assertThat(busLinesWithMostStops.size()).isLessThanOrEqualTo(10);
        assertThat(busLinesWithMostStops.get(0).getLineNumber()).isEqualTo(1);
        assertThat(busLinesWithMostStops.get(0).getNumStops()).isEqualTo(3);
    }
}
