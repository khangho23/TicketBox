package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
	@Id
	@Column
	private String id;
	@Column
	private String name;
	@Column
	private Integer yearofmanufacture;
	@Column(name = "poster")
	private String poster;
	@Column
	private Integer time;
	@Column
	@JsonIgnore
	private Integer countryId;
	@Column
	private String describe;
	@Column
	private String trailer;
	@Column
	private String status;
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
