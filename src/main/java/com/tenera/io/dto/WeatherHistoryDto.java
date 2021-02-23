package com.tenera.io.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeatherHistoryDto {
    @JsonProperty("avg_temp")
    private double averageTemperature;
    @JsonProperty("avg_pressure")
    private double averagePressure;
    @JsonProperty("history")
    private List<WeatherDetailDto> historyList;
}
