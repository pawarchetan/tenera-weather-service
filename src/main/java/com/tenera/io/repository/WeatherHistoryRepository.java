package com.tenera.io.repository;

import com.tenera.io.model.City;
import com.tenera.io.model.WeatherHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherHistoryRepository extends CrudRepository<WeatherHistory, Long> {
    List<WeatherHistory> findTop5ByCityOrderByCreatedAtDesc(City city);
}
