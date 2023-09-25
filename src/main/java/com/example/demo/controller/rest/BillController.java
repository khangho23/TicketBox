package com.example.demo.controller.rest;

import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin("*")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> getBillHistory(@RequestParam Optional<Integer> customerId) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.getBillHistory(customerId));
    }
}
