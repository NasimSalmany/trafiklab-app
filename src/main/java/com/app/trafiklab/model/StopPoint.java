package com.app.trafiklab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StopPoint {
    @JsonProperty("StopPointNumber")
    private Integer stopPointNumber;

    @JsonProperty("StopPointName")
    private String stopPointName;
}
