package com.tenera.io.controller;

import com.tenera.io.dto.WeatherDetailDto;
import com.tenera.io.dto.WeatherHistoryDto;
import com.tenera.io.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "WeatherController")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ApiOperation(value = "retrieve weather information by location", response = WeatherDetailDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Weather information successfully persisted and returned to caller"),
            @ApiResponse(code = 500, message = "Something went wrong while fetching or persisting weather information"),
            @ApiResponse(code = 404, message = "No city found for given search location/query")
    })
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeatherDetailDto weatherByLocation(@RequestParam(value = "location") String location) throws Exception {
        return weatherService.weatherByLocation(location);
    }

    @ApiOperation(value = "retrieve weather history by location", response = WeatherHistoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Weather history successfully retrieved and returned to caller"),
            @ApiResponse(code = 500, message = "Something went wrong while fetching or persisting weather information"),
            @ApiResponse(code = 404, message = "No history found for given search location/query")
    })
    @GetMapping("/history")
    public WeatherHistoryDto searchHistoryByCityName(@RequestParam(value = "location") String location) {
        return weatherService.searchHistoryByCityName(location);
    }
}
