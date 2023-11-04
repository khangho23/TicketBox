package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class ReviewDto {
    @Column
    private Integer rate;
    @Column
    private String review;
    @Column
    private String exportdate;
    @Column
    private String name;
}