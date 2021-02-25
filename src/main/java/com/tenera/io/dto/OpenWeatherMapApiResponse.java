package com.tenera.io.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class OpenWeatherMapApiResponse {
    private String name;

    @JsonProperty("main")
    private MetaDataDto metaDataDto;

    @JsonProperty("weather")
    private List<WeatherDto> weatherDtoList;
}
