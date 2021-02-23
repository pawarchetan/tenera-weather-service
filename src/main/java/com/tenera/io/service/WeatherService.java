package com.tenera.io.service;

import com.tenera.io.dto.OpenWeatherMapApiResponse;
import com.tenera.io.dto.WeatherDetailDto;
import com.tenera.io.dto.WeatherHistoryDto;
import com.tenera.io.exception.NoCityFoundException;
import com.tenera.io.model.WeatherHistory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tenera.io.dto.Weather.shouldTakeUmbrella;

@Service
@Log4j2
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherHistoryService weatherHistoryService;

    @Value("${open.weather.map.api.key}")
    private String openWeatherMapApiKey;

    @Value("${open.weather.map.api.url}")
    private String openWeatherMapApiUrl;

    public WeatherService(RestTemplate restTemplate, WeatherHistoryService weatherHistoryService) {
        this.restTemplate = restTemplate;
        this.weatherHistoryService = weatherHistoryService;
    }

    public WeatherDetailDto weatherByLocation(String location) throws Exception {
        try {
            ResponseEntity<OpenWeatherMapApiResponse> response = getWeatherDetailsByApi(location);
            WeatherDetailDto weatherDetailDto = weatherDetailResponse(response, location);
            auditSearchQuery(weatherDetailDto, location);
            return weatherDetailDto;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof HttpClientErrorException.NotFound) {
                throw new NoCityFoundException("city not found");
            } else {
                throw new Exception("internal server error");
            }
        }
    }

    public WeatherHistoryDto searchHistoryByCityName(String name) {
        List<WeatherHistory> weatherHistories = weatherHistoryService.searchHistoryByCityName(name);
        return mapHistories(weatherHistories);
    }

    private void auditSearchQuery(WeatherDetailDto weatherDetailDto, String name) {
        weatherHistoryService.auditSearchHistory(weatherDetailDto);
    }

    private ResponseEntity<OpenWeatherMapApiResponse> getWeatherDetailsByApi(String location) {
        return restTemplate.getForEntity(openWeatherMapApiUrl + "?units=metric&appid="
                + openWeatherMapApiKey + "&q=" + location, OpenWeatherMapApiResponse.class);
    }

    private WeatherDetailDto weatherDetailResponse(ResponseEntity<OpenWeatherMapApiResponse> response, String location) {
        if (response != null && response.hasBody()) {
            boolean shouldTakeUmbrella = decisionToCarryUmbrella(response);
            double temperature = Objects.requireNonNull(response.getBody()).getMetaDataDto().getTemp();
            long pressure = response.getBody().getMetaDataDto().getPressure();
            return WeatherDetailDto.builder()
                    .query(location)
                    .name(response.getBody().getName())
                    .pressure(pressure)
                    .shouldCarryUmbrella(shouldTakeUmbrella)
                    .temperature(temperature)
                    .build();
        }
        return null;
    }

    private boolean decisionToCarryUmbrella(ResponseEntity<OpenWeatherMapApiResponse> response) {
        return Objects.requireNonNull(response.getBody())
                .getWeatherDtoList()
                .stream()
                .anyMatch(weatherDto -> shouldTakeUmbrella(weatherDto.getMain()));
    }

    private WeatherHistoryDto mapHistories(List<WeatherHistory> weatherHistories) {
        double averageTemperature = getAverageTemperature(weatherHistories);
        long averagePressure = getAveragePressure(weatherHistories);
        List<WeatherDetailDto> weatherDetailDtos = weatherHistories.stream()
                .map(this::mapHistory)
                .collect(Collectors.toList());
        return WeatherHistoryDto.builder()
                .averagePressure(averagePressure)
                .averageTemperature(averageTemperature)
                .historyList(weatherDetailDtos)
                .build();
    }

    private double getAverageTemperature(List<WeatherHistory> weatherHistories) {
        double avgTemperature = weatherHistories.stream()
                .mapToDouble(WeatherHistory::getTemperature)
                .average()
                .orElse(Double.NaN);
        return Math.ceil(avgTemperature * 100) / 100;
    }

    private long getAveragePressure(List<WeatherHistory> weatherHistories) {
        return (long) weatherHistories.stream()
                .mapToLong(WeatherHistory::getPressure)
                .average()
                .orElse(0);
    }

    private WeatherDetailDto mapHistory(WeatherHistory weatherHistory) {
        return WeatherDetailDto.builder()
                .shouldCarryUmbrella(weatherHistory.isUmbrella())
                .pressure(weatherHistory.getPressure())
                .temperature(weatherHistory.getTemperature())
                .build();
    }

}
