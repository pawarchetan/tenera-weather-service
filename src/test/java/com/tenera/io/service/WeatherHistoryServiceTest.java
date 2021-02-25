package com.tenera.io.service;

import com.tenera.io.exception.NoHistoryFoundException;
import com.tenera.io.model.SearchQuery;
import com.tenera.io.model.WeatherHistory;
import com.tenera.io.repository.WeatherHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.tenera.io.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherHistoryServiceTest {

    @InjectMocks
    private WeatherHistoryService weatherHistoryService;

    @Mock
    private SearchQueryService searchQueryService;

    @Mock
    private WeatherHistoryRepository weatherHistoryRepository;

    @Test
    public void shouldReturnSearchQueriesWhenQueryIsProvided() {
        List<SearchQuery> searchQueryList = setupSearchQueries();
        List<WeatherHistory> weatherHistories = setupWeatherHistories();

        when(searchQueryService.findByQuery(CITY_NAME)).thenReturn(searchQueryList);
        when(weatherHistoryRepository.findTop5ByCityOrderByCreatedAtDesc(any())).thenReturn(weatherHistories);

        List<WeatherHistory> actualWeatherHistories = weatherHistoryService.searchHistoryByCityName(CITY_NAME);

        assertEquals(1, actualWeatherHistories.size());
        assertEquals(CITY_NAME, actualWeatherHistories.get(0).getCity().getName());
        assertEquals(PRESSURE, actualWeatherHistories.get(0).getPressure());
        assertEquals(TEMPERATURE, actualWeatherHistories.get(0).getTemperature());
    }

    @Test()
    public void shouldReturnNotfoundExceptionWhenNoHistoryPresentForQuery() {
        when(searchQueryService.findByQuery(CITY_NAME)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(NoHistoryFoundException.class, () ->
                weatherHistoryService.searchHistoryByCityName(CITY_NAME));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("No histories found for given query/city"));
    }
}
