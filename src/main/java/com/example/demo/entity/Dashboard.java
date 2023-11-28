package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Dashboard of theater
 * 
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Dashboard {
	/*
	 * id of Dashboard
	 */
	@Column
	private int id;
	/*
	 * month of Dashboard
	 */
	@Column
	private String month;
	/*
	 * color of Dashboard
	 */
	@Column
	private String color;
	/*
	 * totalTicket of Dashboard
	 */
	@Column
	private int totalTicket;
	/*
	 * totalPrice of Dashboard
	 */
	@Column
	private double totalPrice;
	/*
	 * movieName of Dashboard
	 */
	@Column
	private String movieName;
	/*
	 * year of Dashboard
	 */
	@Column
	private int year;
	
	@Column
	private int hour;
	
	@Column
	private String date;
	
	@Column
	private int totalShowtime;
	
	@Column
	private String movieId;
}

