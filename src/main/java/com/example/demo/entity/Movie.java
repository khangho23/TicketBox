package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Transient;

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
	@Column(name = "poster")
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
	
	@Transient
	private List<Language> language;
	
	@Transient
	private List<TypeOfMovie> type;
	
	@Transient
	private List<Actor> actor;
	
	@Transient
	private List<Director> director;
	
	@Column
	private String countryName;
}
