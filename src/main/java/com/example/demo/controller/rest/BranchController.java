package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BranchService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/branch")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(branchService.findAll());
    }
}
