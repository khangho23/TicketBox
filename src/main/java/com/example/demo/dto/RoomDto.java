package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.Room;

import lombok.Data;

@Entity
@Data
public class RoomDto extends Room{
    @Column
    private String moviename;

    @Column
    private String starttime;

    @Column
    private String total;

    @Column
    private int status;
}
