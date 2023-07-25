package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;

import java.util.List;import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController implements BaseController {
    @Autowired
    CustomerService customerService;

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @Override
    @GetMapping("/getById")
    public ResponseEntity<Customer> findById(String id) {
        return ResponseEntity.ok(customerService.findById(Integer.parseInt(id)));
    }
    
    
    @PostMapping("/save")
    public void save(@RequestBody Customer customer) {
        customerService.insert(customer);
    }
}
