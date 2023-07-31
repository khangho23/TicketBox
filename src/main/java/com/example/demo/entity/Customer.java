package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Customer of theater
 * 
 */

@Entity
@Table
@Data
@NoArgsConstructor
public class Customer {
	/*
	 * id of customer
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	/*
	 * name of customer
	 */
	@Column
	private String name;
	/*
	 * password of customer
	 */
	@Column
	private String password;
	/*
	 * email of customer
	 */
	@Column
	private String email;
	/*
	 * phone of customer
	 */
	@Column
	private String phone;
	/*
	 * address of customer
	 */
	@Column
	private String address;
	/*
	 * gender of customer
	 */
	@Column
	private boolean gender;
	/*
	 * keyfacebook of customer
	 */
	@Column
	private String keyfacebook;
	/*
	 * avatar of customer
	 */
	@Column
	private String avatar;
}
