package com.app.trafiklab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineData {
    @JsonProperty("LineNumber")
    private Integer lineNumber;

    @JsonProperty("JourneyPatternPointNumber")
    private Integer journeyPatternPointNumber;

}
