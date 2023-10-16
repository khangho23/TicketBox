package com.example.demo.dto;

import java.sql.Array;
import java.util.List;
import org.seasar.doma.Entity;
import org.seasar.doma.Transient;

import com.example.demo.entity.Movie;

import lombok.Data;

@Data
@Entity
public class requestMovieDto extends Movie {
    @Transient
    private List<Integer> arrayLanguage;
    @Transient
    private List<String> arrayType;
    @Transient
    private List<String> arrayActor;
    @Transient
    private List<String> arrayDirector;
    @Transient
    private String language2;
    @Transient
    private String type2;
    @Transient
    private String actor2;
    @Transient
    private String director2;
}
