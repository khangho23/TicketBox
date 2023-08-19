package com.example.demo.model;

import com.example.demo.entity.Customer;

import lombok.Data;
@Data
public class MailInfo {
	String from;
	String to;
	String subject;
	Customer body;

	public MailInfo(String to, String subject, Customer body) {
		this.from = "Zuhot Cinema <zuhot@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

}
