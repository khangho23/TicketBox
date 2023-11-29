package com.example.demo.entity;


import java.sql.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table
@Data
@NoArgsConstructor
public class MovieConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column
	private String movieId;
	
	@Column
	private String branchId;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private Date createDate;
	
	@Column
	private Date updateDate;
}
