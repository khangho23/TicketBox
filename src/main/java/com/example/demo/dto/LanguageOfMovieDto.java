package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.LanguageOfMovie;

import lombok.Data;

@Data
@Entity
public class LanguageOfMovieDto extends LanguageOfMovie {
    @Column(name = "movieName")
    private String movieName;
    @Column(name = "languageName")
    private String languageName;
}
