package com.example.demo.entity;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Ticket {
	/*
	 * id of ticket
	 */
	@Id
	@SequenceGenerator(sequence = "ticket_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private Integer id;
	/*
	 * seatDetailsId of ticket
	 */
	@Column(name = "seatdetailsid")
	private Integer seatDetailsId;
	/*
	 * showtimeId of ticket
	 */
	@Column(name = "showtimeid")
	private Integer showtimeId;
	/*
	 * totalPrice of ticket
	 */
	@Column(name = "totalprice")
	private double totalPrice;
	/*
	 * createDate of ticket
	 */
	@Column(name = "createdate", insertable = false, updatable = false)
	private Date createDate;
	/*
	 * vat of ticket
	 */
	@Column
	private Double vat;
	/*
	 * billid of ticket
	 */
	@Column(name = "billid")
	private Integer billId;
}
