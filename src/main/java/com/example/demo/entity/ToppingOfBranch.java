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
public class ToppingOfBranch {
	@Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "branchid")
    private String branchId;

    @Column(name = "toppingid")
    private String toppingId;

    @Column
    private int quantity;

    @Column
    private double price;
}
