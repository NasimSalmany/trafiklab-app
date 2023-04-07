package com.app.trafiklab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusLine {
    private Integer lineNumber;
    private int numStops;
    private Set<String> listOfStopPointNames;
}
