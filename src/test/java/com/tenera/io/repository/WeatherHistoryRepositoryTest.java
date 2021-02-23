package com.tenera.io.repository;

import com.tenera.io.model.City;
import com.tenera.io.model.WeatherHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WeatherHistoryRepositoryTest {

    @Autowired
    private WeatherHistoryRepository weatherHistoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void shouldReturnEmptyWeatherHistoryReportWhenCityDoesntExistInDB() {
        City city = cityRepository.save(new City(" "));
        List<WeatherHistory> weatherHistories = weatherHistoryRepository.findTop5ByCityOrderByCreatedAtDesc(city);

        assertEquals(0, weatherHistories.size());
    }

    @Test
    public void shouldReturnWeatherHistoryReportWhenCityExistInDB() {
        City city = cityRepository.save(new City("Berlin"));
        WeatherHistory weatherHistory = new WeatherHistory(city, 11, 23, true);
        weatherHistoryRepository.save(weatherHistory);

        List<WeatherHistory> weatherHistories = weatherHistoryRepository.findTop5ByCityOrderByCreatedAtDesc(city);

        assertEquals(1, weatherHistories.size());
        assertEquals(11, weatherHistories.get(0).getTemperature());
        assertEquals(23, weatherHistories.get(0).getPressure());
        assertTrue(weatherHistories.get(0).isUmbrella());
    }
}
