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
	private Integer yearofmanufacture;
	@Column
	private String poster;
	@Column
	private Integer time;
	@Column
	private String describe;
	@Column
	private String trailer;
	@Column
	private String status;
	@Column
	private Integer limitage;
}
