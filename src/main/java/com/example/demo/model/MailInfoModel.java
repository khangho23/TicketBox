package com.example.demo.model;

import com.example.demo.entity.Customer;

import lombok.Data;

@Data
public class MailInfoModel {
	String from;
	String to;
	String subject;
	Object body;

	public MailInfoModel(String to, String subject, Customer body) {
		this.from = "Zuhot Cinema <zuhot@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

	public MailInfoModel(String to, String subject, SendOrderModel body) {
		this.from = "Zuhot Cinema <zuhot@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
