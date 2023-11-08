package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *
 *	Country of movie
 *
 */
@Entity
@NoArgsConstructor
@Table
@Data
public class Country {
	/*
	 * id of country
	 */
	@Id
	@Column
	private Integer id;
	/*
	 * name of country
	 */
	@Column
	private String name;
}
