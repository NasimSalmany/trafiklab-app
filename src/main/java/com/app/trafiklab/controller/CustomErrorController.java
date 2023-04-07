package com.app.trafiklab.controller;

import com.app.trafiklab.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @RequestMapping
    public ErrorMessage handleError(HttpServletRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                "",
                "Resource Not Found");
    }
}
