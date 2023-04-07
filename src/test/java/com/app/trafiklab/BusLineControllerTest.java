package com.app.trafiklab;

import com.app.trafiklab.controller.BusLineController;
import com.app.trafiklab.model.BusLine;
import com.app.trafiklab.service.BusLineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusLineController.class)
public class BusLineControllerTest {

    @MockBean
    private BusLineService busLineService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetBuslinesWithMostStopsThenShouldReturnTop10Result() throws Exception {
        List<BusLine> busLines = new ArrayList<>();
        BusLine busLine = new BusLine();
        busLine.setLineNumber(636);
        busLine.setNumStops(232);
        busLine.setListOfStopPointNames(new HashSet<>(Arrays.asList("Norrtälje busstation","Älmsta busstation")));
        busLines.add(busLine);

        when(busLineService.getBusLinesWithMostStops()).thenReturn(busLines);

        mockMvc.perform(get("/buslines/most-stops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].lineNumber").value("636"))
                .andExpect(jsonPath("$[0].numStops").value("232"))
                .andExpect(jsonPath("$[0].listOfStopPointNames.size()").value(2));
    }
}
