package com.example.demo.service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.dao.TicketDao;
import com.example.demo.dao.TokenVnpayDao;
import com.example.demo.dto.TicketDto;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.TokenVnpay;
import com.example.demo.exception.InvalidRequestParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenVnpayService {
	@Autowired
	TokenVnpayDao tokenVnpayDao;

	public List<Ticket> findAll() {
		return null;
	}

	public Optional<Ticket> findById(Integer id) throws InvalidRequestParameterException {
		return Optional.empty();
	}

	public void insert(TokenVnpay tokenVnpay) throws InvalidRequestParameterException {
		tokenVnpayDao.insert(tokenVnpay);
	}

	public TokenVnpay findByCustomerId(Optional<Integer> customerId) throws InvalidRequestParameterException {
		customerId.orElseThrow();
		return tokenVnpayDao.findByCustomerId(customerId.get());
	}
}
