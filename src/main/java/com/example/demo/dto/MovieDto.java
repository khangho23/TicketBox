package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.Movie;

import lombok.Data;

@Data
@Entity
public class MovieDto extends Movie {

    @Column(name = "MOVIE_TYPES")
    private String movieTypeName;

    @Column(name = "ACTORS")
    private String actorsName;

    @Column(name = "directors")
    private String directorsName;

    @Column(name = "LANGUAGES")
    private String languagesName;

    @Column(name = "COUNTRY")
    private String countryName;

    @Column(name = "MOVIE_TYPES_ID")
    private String movieTypeId;

    @Column
    private double rate;
}
