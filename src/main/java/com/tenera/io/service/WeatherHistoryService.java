package com.tenera.io.service;

import com.tenera.io.dto.WeatherDetailDto;
import com.tenera.io.exception.NoHistoryFoundException;
import com.tenera.io.model.City;
import com.tenera.io.model.SearchQuery;
import com.tenera.io.model.WeatherHistory;
import com.tenera.io.repository.CityRepository;
import com.tenera.io.repository.SearchQueryRepository;
import com.tenera.io.repository.WeatherHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherHistoryService {

    private final CityRepository cityRepository;
    private final WeatherHistoryRepository weatherHistoryRepository;
    private final SearchQueryRepository searchQueryRepository;

    public WeatherHistoryService(WeatherHistoryRepository weatherHistoryRepository, SearchQueryRepository searchQueryRepository, CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        this.weatherHistoryRepository = weatherHistoryRepository;
        this.searchQueryRepository = searchQueryRepository;
    }

    public void auditSearchHistory(WeatherDetailDto weatherDetailDto) {
        City city = getOrCreateCity(weatherDetailDto);
        searchQueryRepository.save(new SearchQuery(weatherDetailDto.getQuery(), city));
        WeatherHistory weatherHistory = new WeatherHistory(city, weatherDetailDto.getTemperature(),
                weatherDetailDto.getPressure(), weatherDetailDto.isShouldCarryUmbrella());
        weatherHistoryRepository.save(weatherHistory);
    }

    public List<WeatherHistory> searchHistoryByCityName(String name) {
        List<SearchQuery> searchQueries = searchQueryRepository.findByQuery(name);
        if (searchQueries == null || searchQueries.isEmpty()) {
            throw new NoHistoryFoundException("No histories found for given query/city");
        }
        return weatherHistoryRepository.findTop5ByCityOrderByCreatedAtDesc(searchQueries.get(0).getCity());
    }

    private City getOrCreateCity(WeatherDetailDto weatherDetailDto) {
        City city = cityRepository.findByName(weatherDetailDto.getName());
        if (city == null) {
            city = new City(weatherDetailDto.getName());
            city = cityRepository.save(city);
        }
        return city;
    }
}
