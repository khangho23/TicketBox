package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class ToppingDetails {
    @Column
    private Integer id;

    @Column(name = "toppingofbranch")
    private Integer toppinngOfBranchId;

    @Column(name = "pricewhenbuy")
    private double priceWhenBuy;

    @Column
    private int quantity;
}
