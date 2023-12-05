package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.Staff;

import lombok.Data;

@Entity
@Data
public class StaffDto extends Staff {
    @Column(name = "branchName")
    private String branchName;
    @Column(name = "formatted_birthday")
    private String formatted_birthday;
    @Column(name = "formatted_gender")
    private String formatted_gender;
    @Column(name = "formatted_role")
    private String formatted_role;
}