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

/*
 * 
 * showtime of theater
 * 
 */

@Entity
@Table
@Data
@NoArgsConstructor
public class ShowTime {
	/*
	 * id of showtime
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	/*
	 * roomId of showtime
	 */
	@Column
	private String roomId;
	/*
	 * movieId of showtime
	 */
	@Column
	private String movieId;
	/*
	 * dimensionId of showtime
	 */
	@Column
	private int dimensionId;
	/*
	 * showDate of showtime
	 */
	@Column
	private Date showDate;
	/*
	 * startTime of showtime
	 */
	@Column
	private String startTime;
	/*
	 * price of showtime
	 */
	@Column
	private double price;

}
