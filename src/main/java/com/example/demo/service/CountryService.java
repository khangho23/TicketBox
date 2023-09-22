package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CountryDao;
import com.example.demo.entity.Country;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class CountryService implements BaseService<Country, Integer> {

	@Autowired
	CountryDao countryDao;

	@Override
	public List<Country> findAll() {
		return countryDao.findAll();
	}

	@Override
	public Optional<Country> findById(Integer id) throws InvalidRequestParameterException {
		return countryDao.findById(id);
	}

}
