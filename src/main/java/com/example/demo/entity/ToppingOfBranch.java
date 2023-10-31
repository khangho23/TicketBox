package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class ToppingOfBranch {
    @Column(name = "billid")
    private Integer billId;

    @Column(name = "branchid")
    private Integer branchId;

    @Column(name = "toppingid")
    private Integer toppingId;

    @Column
    private int quantity;

    @Column
    private double price;
}
