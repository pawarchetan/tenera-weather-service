package com.tenera.io.repository;

import com.tenera.io.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void shouldReturnCityWhenValidCityNameProvided() {
        cityRepository.save(new City("Berlin"));
        City city = cityRepository.findByName("Berlin");

        assertNotNull(city);
        assertEquals("Berlin", city.getName());
    }
}

