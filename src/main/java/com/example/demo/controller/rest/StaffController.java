package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Staff;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.StaffService;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin("*")
public class StaffController {
	@Autowired
	private StaffService staffService;

	@PostMapping("/insert")
	public void insert(@RequestBody Staff staff) throws InvalidRequestParameterException{
		staffService.insert(staff);
	}
}
