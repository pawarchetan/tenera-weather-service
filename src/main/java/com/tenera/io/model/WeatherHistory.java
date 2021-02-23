package com.tenera.io.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WeatherHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @ManyToOne
    @JoinColumn(name="city_id", nullable=false)
    private City city;

    private double temperature;
    private double pressure;
    private boolean umbrella;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    public WeatherHistory(City city, double temperature, double pressure, boolean umbrella) {
        this.city = city;
        this.temperature = temperature;
        this.pressure = pressure;
        this.umbrella = umbrella;
    }

}
