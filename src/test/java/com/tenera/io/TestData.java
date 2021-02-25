package com.tenera.io;

import com.tenera.io.dto.MetaDataDto;
import com.tenera.io.dto.OpenWeatherMapApiResponse;
import com.tenera.io.dto.WeatherDto;
import com.tenera.io.model.City;
import com.tenera.io.model.SearchQuery;
import com.tenera.io.model.WeatherHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestData {

    public static final String CITY_NAME = "Berlin";
    public static final double TEMPERATURE = 12.33;
    public static final long PRESSURE = 1234;
    public static final long ID = 1;

    public static City setupCity() {
        return new City(CITY_NAME);
    }

    public static SearchQuery setupSearchQuery() {
        return new SearchQuery(CITY_NAME, setupCity());
    }

    public static List<SearchQuery> setupSearchQueries() {
        List<SearchQuery> searchQueryList = new ArrayList<>();
        searchQueryList.add(setupSearchQuery());
        return searchQueryList;
    }

    public static WeatherHistory setupWeatherHistory() {
        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setId(ID);
        weatherHistory.setPressure(PRESSURE);
        weatherHistory.setTemperature(TEMPERATURE);
        weatherHistory.setCreatedAt(new Date());
        weatherHistory.setCity(setupCity());
        return weatherHistory;
    }

    public static List<WeatherHistory> setupWeatherHistories() {
        List<WeatherHistory> weatherHistories = new ArrayList<>();
        weatherHistories.add(setupWeatherHistory());
        return weatherHistories;
    }

    public static WeatherDto setupWeatherDto() {
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setMain("Main");
        return weatherDto;
    }

    public static List<WeatherDto> setupWeatherDtos() {
        List<WeatherDto> weatherDtos = new ArrayList<>();
        weatherDtos.add(setupWeatherDto());
        return weatherDtos;
    }

    public static MetaDataDto setupMetaDataDto() {
        MetaDataDto metaDataDto = new MetaDataDto();
        metaDataDto.setTemp(12.23);
        metaDataDto.setPressure(1234);
        return metaDataDto;
    }

    public static OpenWeatherMapApiResponse setupOpenWeatherMapApiResponse() {
        OpenWeatherMapApiResponse openWeatherMapApiResponse = new OpenWeatherMapApiResponse();
        openWeatherMapApiResponse.setName(CITY_NAME);
        openWeatherMapApiResponse.setWeatherDtoList(setupWeatherDtos());
        openWeatherMapApiResponse.setMetaDataDto(setupMetaDataDto());
        return openWeatherMapApiResponse;
    }
}
