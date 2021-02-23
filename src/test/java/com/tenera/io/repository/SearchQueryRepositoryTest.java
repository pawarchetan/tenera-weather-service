package com.tenera.io.repository;

import com.tenera.io.model.City;
import com.tenera.io.model.SearchQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SearchQueryRepositoryTest {
    @Autowired
    private SearchQueryRepository searchQueryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void shouldReturnSearchQueryRecordWhenValidCityNameProvided() {
        City city = cityRepository.save(new City("Berlin"));
        searchQueryRepository.save(new SearchQuery("Berlin", city));

        List<SearchQuery> searchQueryList = searchQueryRepository.findByQuery("Berlin");

        assertNotNull(searchQueryList);
        assertTrue(searchQueryList.size() > 0);
        assertEquals("Berlin", searchQueryList.get(0).getQuery());
    }
}
