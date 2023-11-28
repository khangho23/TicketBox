package com.example.demo.dto;

import java.sql.Timestamp;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class ReviewDto {
    @Column
    private Double rate;
    @Column
    private String review;
    @Column
    private String exportdate;
    @Column
    private String name;
    @Column
    private String avatar;
}