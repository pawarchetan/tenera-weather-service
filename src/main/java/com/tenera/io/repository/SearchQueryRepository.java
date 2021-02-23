package com.tenera.io.repository;

import com.tenera.io.model.SearchQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchQueryRepository extends CrudRepository<SearchQuery, Long> {
    List<SearchQuery> findByQuery(String query);
}
