package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CountryDao;
import com.example.demo.entity.Country;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class CountryService {

	@Autowired
	CountryDao countryDao;

	public List<Country> findAll() {
		return countryDao.findAll();
	}
}
