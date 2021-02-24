package com.tenera.io.service;

import com.tenera.io.dto.WeatherDetailDto;
import com.tenera.io.exception.NoHistoryFoundException;
import com.tenera.io.model.City;
import com.tenera.io.model.SearchQuery;
import com.tenera.io.model.WeatherHistory;
import com.tenera.io.repository.WeatherHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherHistoryService {

    private final CityService cityService;
    private final SearchQueryService searchQueryService;
    private final WeatherHistoryRepository weatherHistoryRepository;

    public WeatherHistoryService(WeatherHistoryRepository weatherHistoryRepository, SearchQueryService searchQueryService, CityService cityService) {
        this.cityService = cityService;
        this.weatherHistoryRepository = weatherHistoryRepository;
        this.searchQueryService = searchQueryService;
    }

    public void auditSearchHistory(WeatherDetailDto weatherDetailDto) {
        City city = getOrCreateCity(weatherDetailDto);
        searchQueryService.createByQueryAndCity(weatherDetailDto.getQuery(), city);
        WeatherHistory weatherHistory = new WeatherHistory(city, weatherDetailDto.getTemperature(),
                weatherDetailDto.getPressure(), weatherDetailDto.isShouldCarryUmbrella());
        weatherHistoryRepository.save(weatherHistory);
    }

    public List<WeatherHistory> searchHistoryByCityName(String name) {
        List<SearchQuery> searchQueries = searchQueryService.findByQuery(name);
        if (searchQueries == null || searchQueries.isEmpty()) {
            throw new NoHistoryFoundException("No histories found for given query/city");
        }
        return weatherHistoryRepository.findTop5ByCityOrderByCreatedAtDesc(searchQueries.get(0).getCity());
    }

    private City getOrCreateCity(WeatherDetailDto weatherDetailDto) {
        City city = cityService.getByName(weatherDetailDto.getName());
        if (city == null) {
            city = cityService.create(weatherDetailDto.getName());
        }
        return city;
    }
}
