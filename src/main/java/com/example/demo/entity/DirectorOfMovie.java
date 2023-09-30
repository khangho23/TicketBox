package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * DirectorOfMovie of movie
 * 
 */

@Data
@Entity
@NoArgsConstructor
@Table
public class DirectorOfMovie {
	/*
	 * movieId of director
	 */
	@Column
	private String movieId;
	/*
	 * directorId of director
	 */
	@Column
	private Integer directorId;
}
