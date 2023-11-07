package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BranchDao;
import com.example.demo.entity.Branch;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class BranchService {

	@Autowired
	BranchDao branchDao;

	public List<Branch> findAll() {
		return branchDao.findAll();
	}

	public Optional<Branch> findById(String id) throws InvalidRequestParameterException {
		return branchDao.findById(id);
	}

}
