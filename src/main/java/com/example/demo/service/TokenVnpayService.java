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
public class TokenVnpayService implements BaseService<Ticket, Integer> {
	@Autowired
	TokenVnpayDao tokenVnpayDao;

	@Override
	public List<Ticket> findAll() {
		return null;
	}

	@Override
	public Optional<Ticket> findById(Integer id) throws InvalidRequestParameterException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	public void insert(TokenVnpay tokenVnpay) throws InvalidRequestParameterException {
		// TODO Auto-generated method stub
		tokenVnpayDao.insert(tokenVnpay);
	}

	public TokenVnpay findByCustomerId(Optional<Integer> customerId) throws InvalidRequestParameterException {
		// TODO Auto-generated method stub
		customerId.orElseThrow();

		return tokenVnpayDao.findByCustomerId(customerId.get());
	}
}
