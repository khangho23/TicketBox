package com.example.demo.dto;

import java.util.List;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Transient;

import com.example.demo.entity.Movie;

import lombok.Data;

@Data
@Entity
public class requestMovieDto extends Movie {
    @Column(name = "arrayLanguage")
    @Transient
    private List<Integer> arrayLanguage;
    @Column(name = "arrayType")
    @Transient
    private List<String> arrayType;
    @Column(name = "arrayActor")
    @Transient
    private List<Integer> arrayActor;
    @Column(name = "arrayDirector")
    @Transient
    private List<Integer> arrayDirector;
}
