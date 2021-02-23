package com.tenera.io.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"name", "query"})
public class WeatherDetailDto {

    @JsonProperty("query")
    private String query;

    @JsonProperty("name")
    private String name;

    @JsonProperty("temp")
    private double temperature;

    private long pressure;

    @JsonProperty("umbrella")
    private boolean shouldCarryUmbrella;
}
