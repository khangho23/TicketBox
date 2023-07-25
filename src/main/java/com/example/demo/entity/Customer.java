package com.example.demo.entity;

import org.seasar.doma.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private int gender;
    @Column
    private String idfb;
    @Column
    private String imagefb;
}