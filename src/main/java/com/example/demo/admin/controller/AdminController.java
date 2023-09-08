package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class AdminController {
	@GetMapping("")
	public String index() {
		return "index.html";
	}
}
