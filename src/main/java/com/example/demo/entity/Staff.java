package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
public class Staff {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "branchid")
	private String branchId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "gender")
	private boolean gender;
	
	@Column(name = "password")
	private String password;

	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name="status")
	private boolean status;

	@Column
	private int role;
}
