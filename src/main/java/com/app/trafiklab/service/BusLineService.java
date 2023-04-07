package com.app.trafiklab.service;

import com.app.trafiklab.client.TrafiklabWebClient;
import com.app.trafiklab.model.BusLine;
import com.app.trafiklab.model.LineData;
import com.app.trafiklab.model.StopPoint;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusLineService {
    private final TrafiklabWebClient trafiklabClient;

    public BusLineService(TrafiklabWebClient trafiklabClient) {
        this.trafiklabClient = trafiklabClient;
    }
    public List<BusLine> getBusLinesWithMostStops() {
        // Retrieve all BusLines
        List<LineData> busLines = trafiklabClient.getAllBusLinesByJourModel();

        // Retrieve all StopPoints
        List<StopPoint> stoPoints =  trafiklabClient.getAllBusLinesByStopPointModel();

        // Group busLine data by lineNumber and map each line to jourPointNumber
        Map<Integer, List<Integer>> busLineMap = busLines.stream()
                .collect(Collectors.groupingBy(LineData::getLineNumber,
                        Collectors.mapping(LineData::getJourneyPatternPointNumber, Collectors.toList())));

        // Sort the busLine list and get top 10 result
        Map<Integer, List<Integer>> busLinesWithMostStops = busLineMap.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> -entry.getValue().size()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        // Map StopPointNumber to StopPointNames
        return busLinesWithMostStops.entrySet().stream()
                .map(entry -> new BusLine(entry.getKey(), entry.getValue().size(), entry.getValue()
                        .stream().map(spn-> mapSPNumberToName(spn, stoPoints)).collect(Collectors.toSet()))).toList();
    }
    public String mapSPNumberToName(Integer lineNumber, List<StopPoint> stopPoints) {
        Map<Integer, String> numberToNameMap = stopPoints.stream()
                .collect(Collectors.toMap(StopPoint::getStopPointNumber, StopPoint::getStopPointName));

        return numberToNameMap.get(lineNumber);

    }
}
