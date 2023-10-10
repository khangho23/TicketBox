package com.example.demo.dto;

import java.sql.Date;
import java.sql.Time;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import com.example.demo.entity.Topping;

import lombok.Data;

@Data
@Entity
public class ToppingDto extends Topping {
	/*
	 * toppingofbranchid of topping
	 */
	@Column
	private Integer toppingofbranchid;
	/*
	 * branchid of topping
	 */
	@Column(name = "branchid")
	private String branch;
	
	/*
	 * quantity of topping
	 */
	@Column(name = "quantity")
	private Integer quantity;
	/*
	 * price of topping
	 */
	@Column(name = "price")
	private Double price;

}
