package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class PosterDto {
    @Column
    private String id;
    @Column
    private String poster;
}
