package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.DimensionService;

@RestController
@RequestMapping("/api/dimension")
@CrossOrigin("*")
public class DimensionController {
    @Autowired
    DimensionService dimensionService;

    @GetMapping({ "/", "" })
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(dimensionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) throws InvalidRequestParameterException {
        return ResponseEntity.ok(dimensionService.findById(id)
                .orElseThrow(() -> new InvalidRequestParameterException("Dimension", RequestParameterEnum.NOT_EXISTS)));
    }
}
