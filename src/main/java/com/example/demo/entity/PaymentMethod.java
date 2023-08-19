package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 *	payment method 
 * 
 */

@Entity
@Table
@Data
@NoArgsConstructor
public class PaymentMethod {
	/*
	 * id of payment method
	 */
	@Id
	@Column
	private String id;
	/*
	 * name of payment method
	 */
	@Column
	private String name;
}
