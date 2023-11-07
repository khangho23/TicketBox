package com.example.demo.dto;

import java.sql.Date;
import java.sql.Time;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.Ticket;

import lombok.Data;

@Data
@Entity
public class TicketDto extends Ticket {
	/*
	 * id of ticket
	 */
	@Column
	private Integer id;
	/*
	 * customerName of ticket
	 */
	@Column(name = "customername")
	private String customerName;
	/*
	 * customerName of ticket
	 */
	@Column(name = "showdate")
	private Date showdate;
	/*
	 * customerName of ticket
	 */
	@Column(name = "starttime")
	private Time startTime;
	/*
	 * seat of ticket
	 */
	@Column(name = "seat")
	private String seat;
	/*
	 * seat_price of ticket
	 */
	@Column(name = "seat_price")
	private double seatPrice;
	/*
	 * seat_pricecommon of ticket
	 */
	@Column(name = "seat_price_common")
	private double seatPriceCommon;
	/*
	 * seatType of ticket
	 */
	@Column(name = "seattype")
	private String seatType;
	/*
	 * seat_pricecommon of ticket
	 */
	@Column(name = "seattype_surchage")
	private double seatTypeSurchage;
	/*
	 * moviename of ticket
	 */
	@Column(name = "moviename")
	private String movieName;
}
