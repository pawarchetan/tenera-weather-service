package com.tenera.io.service;

import com.tenera.io.model.City;
import com.tenera.io.model.SearchQuery;
import com.tenera.io.repository.SearchQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchQueryService {

    private final SearchQueryRepository searchQueryRepository;

    public SearchQueryService(SearchQueryRepository searchQueryRepository) {
        this.searchQueryRepository = searchQueryRepository;
    }

    public SearchQuery createByQueryAndCity(String query, City city) {
        SearchQuery searchQuery = new SearchQuery(query, city);
        return searchQueryRepository.save(searchQuery);
    }

    public List<SearchQuery> findByQuery(String query) {
        return searchQueryRepository.findByQuery(query);
    }

}
