package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Dimension of showtime
 * 
 */
@Entity
@Table
@NoArgsConstructor
@Data
public class Dimension {
	/*
	 * id of dimension
	 */
	@Id
	@Column
	private Integer id;
	/*
	 * name of dimension
	 */
	@Column
	private String name;
	/*
	 * pricesurcharge of dimension
	 */
	@Column
	private double pricesurcharge;
}
