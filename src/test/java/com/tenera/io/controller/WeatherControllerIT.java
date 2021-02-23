package com.tenera.io.controller;

import com.tenera.io.dto.WeatherDetailDto;
import com.tenera.io.dto.WeatherHistoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    private static final String CURRENT_URL = "/current?location=";
    private static final String HISTORY_URL = "/history?location=";

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void shouldReturnWeatherDetailWhenValidCityIsProvided() {
        ResponseEntity<WeatherDetailDto> response = template.getForEntity(base.toString() + CURRENT_URL + "mumbai",
                WeatherDetailDto.class);
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldReturnNotFoundExceptionWhenInValidCityIsProvided() {
        ResponseEntity<WeatherDetailDto> response = template.getForEntity(base.toString() + CURRENT_URL + "xyz",
                WeatherDetailDto.class);
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void shouldReturnHistoryWhenValidQueryIsProvided() {
        template.getForEntity(base.toString() + CURRENT_URL + "mumbai",
                WeatherDetailDto.class);
        ResponseEntity<WeatherHistoryDto> response = template.getForEntity(base.toString() + HISTORY_URL + "mumbai",
                WeatherHistoryDto.class);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getHistoryList().size() > 0);
    }

    @Test
    public void shouldReturnNotFoundExceptionWhenInValidQueryIsProvided() {
        ResponseEntity<WeatherHistoryDto> response = template.getForEntity(base.toString() + HISTORY_URL + " ",
                WeatherHistoryDto.class);
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
    }
}
