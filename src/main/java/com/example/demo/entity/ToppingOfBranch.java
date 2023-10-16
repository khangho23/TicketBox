package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class ToppingOfBranch {
	@Id
	@Column
	private String id;
	
	/*
	 * branchid of topping
	 */
	@Column(name = "branchid")
    private String branchId;
	/*
	 * toppingid of topping
	 */
	@Column(name = "toppingid")
    private String toppingId;
	
	/*
	 * quantity of toppingOfBranch
	 */
	@Column(name = "quantity")
    private Integer quantity;
	
	/*
	 * price of toppingOfBranch
	 */
	@Column(name = "price")
    private String price;
}
