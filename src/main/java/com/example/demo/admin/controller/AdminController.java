package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping({"", "/dash"})
	public String index() {
		return "index.html";
	}
}
