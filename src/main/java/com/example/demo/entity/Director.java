package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Director of movie
 * 
 */

@Entity
@Table
@NoArgsConstructor
@Data
public class Director {
	/*
	 * id of director
	 */
	@Id
	@Column
	private Integer id;
	/*
	 * name of director
	 */
	@Column
	private String name;
}
