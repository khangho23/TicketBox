package com.example.demo.service;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.enums.RequestStatusEnum;
import com.example.demo.dao.TokenVnpayDao;
import com.example.demo.entity.TokenVnpay;
import com.example.demo.exception.InvalidRequestParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenVnpayService {
	@Autowired
	TokenVnpayDao tokenVnpayDao;

	public String insert(Optional<TokenVnpay> tokenVnpay) throws InvalidRequestParameterException {
		tokenVnpay.orElseThrow(() -> 
			new InvalidRequestParameterException("TokenVNPay", RequestParameterEnum.NOTHING));
		tokenVnpayDao.insert(tokenVnpay.get());
		
		return RequestStatusEnum.SUCCESS.getResponse();
	}

	public TokenVnpay findByCustomerId(Optional<Integer> customerId) throws InvalidRequestParameterException {
		customerId.orElseThrow(() -> new InvalidRequestParameterException("TokenVnpay customerId", RequestParameterEnum.NOTHING));
		TokenVnpay tokenVnpay = tokenVnpayDao.findByCustomerId(customerId.get());
//		if (tokenVnpay == null) 
//			throw new InvalidRequestParameterException("Token Vnpay", RequestParameterEnum.NOT_FOUND);
		
		return tokenVnpay;
	}
	
	public void deleteById(Optional<Integer> id) throws InvalidRequestParameterException {
		id.orElseThrow(() -> new InvalidRequestParameterException("TokenVnpay id", RequestParameterEnum.NOTHING));
		tokenVnpayDao.deleteById(id.get());
	}
}
