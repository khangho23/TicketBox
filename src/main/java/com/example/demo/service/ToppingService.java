package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.dao.ToppingDao;
import com.example.demo.dto.ToppingDto;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class ToppingService {

	@Autowired
	ToppingDao toppingDao;
	
	public List<ToppingDto> findByBranchid(Optional<String> branchid) throws InvalidRequestParameterException{
		if (branchid.isEmpty())
			throw new InvalidRequestParameterException("Topping", RequestParameterEnum.NOTHING);
		return toppingDao.findByBranchId(branchid.get());
		
	}
	
	public Object orderTopping(){
		return null;
	}
}
