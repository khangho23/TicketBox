package com.example.demo.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import jakarta.validation.constraints.Null;
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
	@Null
	private Boolean gender;
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

	/*
	 * create date of customer
	 */
	@Column
	private LocalDateTime created_at = LocalDateTime.now();

	/*
	 * token of customer
	 */
	@Column
	private String token;

	/*
	 * active of customer
	 */
	@Column
	private boolean active;

	public Customer(String name, String password, String email, String phone, String address, boolean gender,
			String avatar, String token) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.keyfacebook = null;
		this.avatar = avatar;
		this.token = token;
		this.active = false;
	}
}
