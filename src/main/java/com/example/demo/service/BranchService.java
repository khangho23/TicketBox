package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BranchDao;
import com.example.demo.entity.Branch;

@Service
public class BranchService {

	@Autowired
	BranchDao branchDao;

	public List<Branch> findAll() {
		return branchDao.findAll();
	}
}
