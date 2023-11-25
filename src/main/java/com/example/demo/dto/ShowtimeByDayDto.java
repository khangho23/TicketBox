package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class ShowtimeByDayDto {
    @Column(name = "starttime")
    private String starttime;
    @Column(name = "quantity")
    private Integer quantity;
}
