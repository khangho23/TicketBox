package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * movie
 * 
 */

@Entity
@Table
@Data
@NoArgsConstructor
public class Movie {
	/*
	 * id of movie
	 */
	@Id
	@Column
	private String id;
	/*
	 * country id of movie
	 */
	@Column
	private Integer countryid;
	/*
	 * name of movie
	 */
	@Column
	private String name;
	/*
	 * yearofmanufacture of movie
	 */
	@Column
	private Integer yearofmanufacture;
	/*
	 * poster of movie
	 */
	@Column
	private String poster;
	/*
	 * time of movie
	 */
	@Column
	private Integer time;
	/*
	 * describe of movie
	 */
	@Column
	private String describe;
	/*
	 * trailer of movie
	 */
	@Column
	private String trailer;
	/*
	 * status of movie
	 */
	@Column
	private String status;
	/*
	 * limitage of movie
	 */
	@Column
	private Integer limitage;
}
