package com.example.demo.entity;

import org.seasar.doma.*;

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
	 * branchid of payment method
	 */
	@Column
	private String branchid;
	/*
	 * name of payment method
	 */
	@Column
	private String name;
}
