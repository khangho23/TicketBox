package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Language of movie
 * 
 */

@Entity
@Table
@Data
@NoArgsConstructor
public class Language {
	/*
	 * id of language
	 */
	@Id
	@Column
	private Integer id;
	/*
	 * name of language
	 */
	@Column
	private String name;
}
