package com.app.trafiklab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusStopResponse {

    @JsonProperty("StatusCode")
    private String statusCode;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("ExecutionTime")
    private int executionTime;

    @JsonProperty("ResponseData")
    private BusStopResponseData responseData;

}
