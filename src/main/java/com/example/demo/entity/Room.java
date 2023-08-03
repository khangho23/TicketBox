package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 *	room of theater 
 * 
 */

@Entity
@Data
@NoArgsConstructor
@Table
public class Room {
	/*
	 * id of payment method
	 */
	@Id
	@Column
	private String id;
	/*
	 * brandid of payment method
	 */
	@Column
	private String brandid;
	/*
	 * name of payment method
	 */
	@Column
	private String name;
}
