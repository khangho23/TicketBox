package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin/movie")
public class MovieManagementController {
    @GetMapping("")
    public String index() {
        return "components/movie_management.html";
    }
}
