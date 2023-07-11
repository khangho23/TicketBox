package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.Customer;

@Service
public class CustomerService{
@Autowired
CustomerDao customerDao;
	public Customer findByKey(String email, String password) {
		// TODO Auto-generated method stub
		return customerDao.findByKey(email, password);
	}

}
