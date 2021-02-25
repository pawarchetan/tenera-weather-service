package com.tenera.io.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@NoArgsConstructor
@Table(indexes = @Index(columnList = "query"))
public class SearchQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    private String query;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    public SearchQuery(String query, City city) {
        this.city = city;
        this.query = query;
    }
}
