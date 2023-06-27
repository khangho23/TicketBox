package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UsersDao;

@RestController
public class UsersController {
	
	@Autowired
	private UsersDao dao;
	
	@GetMapping("/users")
	public ResponseEntity<?> selectAll(){
		return ResponseEntity.ok(dao.selectAll());
	}
}
