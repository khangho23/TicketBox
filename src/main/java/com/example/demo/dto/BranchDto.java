package com.example.demo.dto;

import java.util.List;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Transient;

import com.example.demo.entity.Branch;

@Entity
public class BranchDto extends Branch{
    @Column
    private String name;
    @Column
    private String arrRoomString;

    @Transient
    private List<String> rooms;
}
