package com.example.demo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Branch;
import com.example.demo.service.BranchService;
import com.example.demo.service.RoomService;

@RestController
@RequestMapping("/admin/management-payment")
public class PaymentManagementController {
	@Autowired
	RoomService roomService;
	
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }
    
    @GetMapping("/branch")
    public ResponseEntity<?> findByBranchId() {
        return null;
    }
}
