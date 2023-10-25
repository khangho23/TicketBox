package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paymentmethoddetails")
@Data
@NoArgsConstructor
public class PaymentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name = "paymethodid")
	private String payMethodId;
	
	@Column(name = "staffid")
	private String staffId;
	
	@Column(name = "billid")
	private Integer billId;
	
	@Column(name = "paystatus")
	private Integer status;

	@Column(name = "vnp_transactionno")
	private String vnp_TransactionNo;

	@Column
	private double amout;
}
