package com.app.trafiklab.controller;

import com.app.trafiklab.model.BusLine;
import com.app.trafiklab.service.BusLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buslines")
public class BusLineController {
    private static final Logger logger = LoggerFactory.getLogger(BusLineController.class);

    private final BusLineService busLineService;

    public BusLineController(BusLineService busLineService) {
        this.busLineService = busLineService;
    }

    @GetMapping("/most-stops")
    @ResponseBody
    public List<BusLine> getBusLinesWithMostStops() {
        logger.info("Received request to getBusLinesWithMostStops");
        return busLineService.getBusLinesWithMostStops();
    }
}

