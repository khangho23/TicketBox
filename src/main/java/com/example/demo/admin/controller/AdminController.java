package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping({ "", "/dash", "/managementMovie", "/managementActor", "/management-showtime",
			"/help" })
	public String index() {
		return "index.html";
	}

	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
}
