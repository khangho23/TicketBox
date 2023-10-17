package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.Room;

import lombok.Data;

@Data
@Entity
public class RoomDto extends Room {
    @Column(name = "branchName")
    private String branchName;
}
