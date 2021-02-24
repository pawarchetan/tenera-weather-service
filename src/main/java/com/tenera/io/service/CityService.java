package com.tenera.io.service;

import com.tenera.io.model.City;
import com.tenera.io.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getByName(String name) {
        return cityRepository.findByName(name);
    }

    public City create(String name) {
        City city = new City(name);
        return cityRepository.save(city);
    }
}
