package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(immutable = true)
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	@Column
	private int id;
	@Column
	private String name;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String phone;
	@Column
	private String address;
	@Column
	private int gender;
	@Column
	private String idfb;
	@Column
	private String imagefb;
}
