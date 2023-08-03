package com.example.demo.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Favorite of customer
 * 
 */

@Entity
@NoArgsConstructor
@Data
@Table
public class Favorite {
	/*
	 * customerId of favorite
	 */
	@Id
	@Column
	private Integer customerId;
	/*
	 * movieId of favorite
	 */
	@Id
	@Column
	private String movieId;
	/*
	 * createDate of favorite
	 */
	@Column
	private Date createDate = new Date();
}
