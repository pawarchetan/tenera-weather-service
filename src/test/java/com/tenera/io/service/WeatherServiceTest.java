package com.tenera.io.service;

import com.tenera.io.dto.OpenWeatherMapApiResponse;
import com.tenera.io.dto.WeatherHistoryDto;
import com.tenera.io.exception.TeneraApplicationException;
import com.tenera.io.model.WeatherHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.tenera.io.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherHistoryService weatherHistoryService;

    @Test
    public void shouldThrowTeneraApplicationExceptionWhenIssueWithSearchingAuditHistory() {
        OpenWeatherMapApiResponse openWeatherMapApiResponse = setupOpenWeatherMapApiResponse();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(openWeatherMapApiResponse, HttpStatus.OK));
        doThrow(HttpClientErrorException.class).when(weatherHistoryService).auditSearchHistory(any());

        Exception exception = assertThrows(TeneraApplicationException.class, () -> weatherService.weatherByLocation("Berlin"));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Something went wrong!"));
    }

    @Test
    public void shouldReturnHistoryWhenCityIsProvided() {
        List<WeatherHistory> weatherHistories = setupWeatherHistories();

        when(weatherHistoryService.searchHistoryByCityName(CITY_NAME)).thenReturn(weatherHistories);

        WeatherHistoryDto actualWeatherHistoryDto = weatherService.searchHistoryByCityName(CITY_NAME);

        assertNotNull(actualWeatherHistoryDto);
        assertEquals(PRESSURE, actualWeatherHistoryDto.getAveragePressure());
        assertEquals(TEMPERATURE, actualWeatherHistoryDto.getAverageTemperature());
    }
}
