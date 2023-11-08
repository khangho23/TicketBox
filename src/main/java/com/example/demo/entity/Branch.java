package com.example.demo.entity;

import org.seasar.doma.*;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Branch of theater
 * 
 */
@Entity
@Data
@NoArgsConstructor
@Table
public class Branch {
	/*
	 *	id of branch 
	 */
	@Id
	@Column
	private String id;
	/*
	 *	name of branch 
	 */
	@Column
	private String name;
	/*
	 *	address of branch 
	 */
	@Column
	private String address;
	/*
	 *	phone of branch 
	 */
	@Column
	private String phone;
}
