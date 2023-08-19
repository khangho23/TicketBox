package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

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
	private Integer id;
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
