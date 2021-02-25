package com.tenera.io.service;

import com.tenera.io.model.City;
import com.tenera.io.model.SearchQuery;
import com.tenera.io.repository.SearchQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.tenera.io.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchQueryServiceTest {

    @InjectMocks
    private SearchQueryService searchQueryService;

    @Mock
    private SearchQueryRepository searchQueryRepository;

    @Test
    public void shouldCreateSearchQueryWhenQueryAndCityIsProvided() {
        City city = setupCity();
        SearchQuery searchQuery = setupSearchQuery();

        when(searchQueryRepository.save(any())).thenReturn(searchQuery);

        SearchQuery createdSearchQuery = searchQueryService.createByQueryAndCity(CITY_NAME, city);

        assertEquals(CITY_NAME, createdSearchQuery.getCity().getName());
        assertEquals(CITY_NAME, createdSearchQuery.getQuery());
    }

    @Test
    public void shouldReturnSearchQueriesWhenQueryIsProvided() {
        List<SearchQuery> searchQueryList = setupSearchQueries();

        when(searchQueryRepository.findByQuery(any())).thenReturn(searchQueryList);

        List<SearchQuery> expectedSearchQueryList = searchQueryService.findByQuery(CITY_NAME);

        assertEquals(1, expectedSearchQueryList.size());
    }
}
