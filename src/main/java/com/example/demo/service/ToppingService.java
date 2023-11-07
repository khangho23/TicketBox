package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.dao.ToppingDao;
import com.example.demo.dao.ToppingDetailsDao;
import com.example.demo.dto.ToppingDto;
import com.example.demo.entity.ToppingDetails;
import com.example.demo.entity.ToppingOfBranch;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class ToppingService {

	@Autowired
	ToppingDao toppingDao;
	
	@Autowired
	ToppingDetailsDao toppingDetailsDao;
	
	public ToppingOfBranch findToppingOfBranchById(Integer id) throws InvalidRequestParameterException{
		return toppingDao.findToppingOfBranchById(id);
	}
	
	
	public List<ToppingDto> findByBranchid(Optional<String> branchid) throws InvalidRequestParameterException{
		if (branchid.isEmpty())
			throw new InvalidRequestParameterException("Topping", RequestParameterEnum.NOTHING);
		return toppingDao.findByBranchId(branchid.get());
		
	}
	
	public String orderTopping(Optional<ToppingDetails> toppingDetails) throws InvalidRequestParameterException{
		if (toppingDetails.isEmpty()) throw new InvalidRequestParameterException("Topping Details", RequestParameterEnum.NOTHING);
		
		toppingDetailsDao.insert(toppingDetails.get());
		return RequestStatusEnum.SUCCESS.getResponse();
	}
	
	public String updateToppingOfBranchAfterOrdered(Integer id, int quantity) throws InvalidRequestParameterException {
		if (quantity != 0) toppingDao.updateToppingOfBranchAfterOrdered(id, quantity);
		return RequestStatusEnum.SUCCESS.getResponse();
	}
}
