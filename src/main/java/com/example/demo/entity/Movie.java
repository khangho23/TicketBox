package com.example.demo.entity;

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
	private int year_of_manufacture;
	@Column
	private String image;
	@Column
	private String director;
	@Column
	private String national;
	@Column
	private String time;
	@Column
	private String describe;
	@Column
	private String trailer;
}
