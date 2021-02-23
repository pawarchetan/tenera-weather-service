package com.tenera.io.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"id", "openApiId", "name"})
public class WeatherDetailDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("temp")
    private double temperature;

    private double pressure;

    @JsonProperty("umbrella")
    private boolean shouldCarryUmbrella;
}
