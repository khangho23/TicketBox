package com.example.demo.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/management-movie")
public class MovieManagementController {
    @GetMapping("/")
    public ResponseEntity<?> index() {
        return null;
    }
}
