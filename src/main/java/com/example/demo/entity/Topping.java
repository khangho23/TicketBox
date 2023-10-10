package com.example.demo.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Topping {
	@Id
	@Column
	private String id;
	/*
	 * name of topping
	 */
	@Column(name = "name")
    private String name;
	
	/*
	 * logo of topping
	 */
	@Column(name = "logo")
	private String logo;
}
