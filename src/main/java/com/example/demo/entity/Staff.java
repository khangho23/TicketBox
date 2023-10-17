package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class Staff {
    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String branchId;
    
    @Column
    private int gender;
    
    @Column
    private Date birthday;
    
    @Column
    private String phone;
    
    @Column
    private int status;
    
}
