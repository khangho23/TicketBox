package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	private Integer countryId;
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
	
	@Column(name = "MOVIE_TYPES")
    String movieTypeName;
	
	@Column(name = "ACTORS")
    String actorsName;
//	
//	@Column(name = "BRANCH")
//    String branchesName;
	
//	@Column(name = "DIMENSION")
//    String dimensionName;
	
	@Column(name = "DIRECTORS")
    String directorsName;
	
	@Column(name = "LANGUAGES")
    String languagesName;

	@Column(name = "COUNTRY")
	String countryName;
//	
//	@Column(name = "SHOWTIME")
//    String showtimeName;
//	
//	@Column(name = "SHOWDATE")
//    String showdateName;
}
