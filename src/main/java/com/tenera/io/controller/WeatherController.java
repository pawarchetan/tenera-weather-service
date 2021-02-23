package com.tenera.io.controller;

import com.tenera.io.dto.WeatherDetailDto;
import com.tenera.io.dto.WeatherHistoryDto;
import com.tenera.io.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public WeatherDetailDto weatherByLocation(@RequestParam(value = "location") String location) throws Exception {
        return weatherService.weatherByLocation(location);
    }

    @GetMapping("/history")
    public WeatherHistoryDto searchHistoryByCityName(@RequestParam(value = "location") String location) {
        return weatherService.searchHistoryByCityName(location);
    }
}
