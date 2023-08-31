package com.example.demo.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
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
public class Showtime {
	/*
	 * id of customer
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	/*
	 * id of showtime
	 */
	@Column
	private String roomid;
	/*
	 * roomid of showtime
	 */
	@Column
	private String movieid;
	/*
	 * movieid of showtime
	 */
	@Column
	private int dimensionid;
	/*
	 * dimensionid of showtime
	 */
	@Column
	private Date showdate;
	/*
	 * starttime of showtime
	 */
	@Column
	private String starttime;
	/*
	 * price of showtime
	 */
	@Column
	private double price;

}
