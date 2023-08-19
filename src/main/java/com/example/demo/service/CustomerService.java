package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.Customer;
import com.example.demo.model.MailInfo;

import jakarta.mail.MessagingException;

@Service
public class CustomerService implements BaseService<Customer, Integer> {
	@Autowired
	CustomerDao customerDao;
	@Autowired
	EmailService emailService;

	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
	}

	@Override
	public Optional<Customer> findById(Integer id) {
		return customerDao.findById(id);
	}

	public Optional<Customer> findByEmail(String email) {
		return customerDao.findByEmail(email);
	}

	public void insert(Customer customer) {
		customerDao.insert(customer);
	}

	public String sendCode(Customer customer) throws MessagingException {
		return emailService.sendCode(new MailInfo(customer.getEmail(), "Mã xác minh tài khoản của bạn trên Zuhot Cinema", customer));
	}

	public Customer findByKey(String email, String password) {
		// TODO Auto-generated method stub
		return customerDao.findByKey(email, password);
	}

}
