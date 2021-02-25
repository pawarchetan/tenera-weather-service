package com.tenera.io.service;

import com.tenera.io.model.City;
import com.tenera.io.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.tenera.io.TestData.CITY_NAME;
import static com.tenera.io.TestData.setupCity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Test
    public void shouldCreateCityWhenNameIsProvided() {
        City city = setupCity();

        when(cityRepository.save(any())).thenReturn(city);

        City createdCity = cityService.create(CITY_NAME);

        assertEquals(CITY_NAME, createdCity.getName());
    }

    @Test
    public void shouldReturnCityWhenNameIsProvided() {
        City city = setupCity();

        when(cityRepository.findByName(CITY_NAME)).thenReturn(city);

        City persistedCity = cityService.getByName(CITY_NAME);

        assertEquals("Berlin", persistedCity.getName());
    }
}
