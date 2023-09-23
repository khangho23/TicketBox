package com.example.demo.dto;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import com.example.demo.entity.Ticket;

import lombok.Data;

@Data
@Entity
public class TicketDto extends Ticket {
	/*
	 * id of ticket
	 */
	@Id
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
	private Date startTime;
	/*
	 * seat of ticket
	 */
	@Column(name = "seat")
	private String seat;
	/*
	 * seatType of ticket
	 */
	@Column(name = "seattype")
	private String seatType;
	/*
	 * totalPrice of ticket
	 */
	@Column(name = "totalprice")
	private double totalPrice;
	/*
	 * createDate of ticket
	 */
	@Column(name = "createdate")
	private Date createDate;
	/*
	 * vat of ticket
	 */
	@Column
	private Double vat;
	/*
	 * exportStatus of ticket
	 */
	@Column(name = "exportstatus")
	private Boolean exportStatus;
}
