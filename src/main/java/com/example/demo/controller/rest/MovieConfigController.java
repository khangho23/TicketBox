package com.example.demo.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BillTicketDto;
import com.example.demo.entity.MovieConfig;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.MovieConfigService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/movieConfig")
public class MovieConfigController {
	@Autowired
	private MovieConfigService movieConfigService;
	
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody MovieConfig movieConfig )throws InvalidRequestParameterException {
        return ResponseEntity.ok(movieConfigService.update(movieConfig));
    }
    
    @RequestMapping("/findAllByMovieId")
    public ResponseEntity<?> findAllByMovieId (@RequestParam String movieId )throws InvalidRequestParameterException {
        return ResponseEntity.ok(movieConfigService.findAllByMovieId(movieId));
    }
    @RequestMapping("/findAll")
    public ResponseEntity<?> findAll()throws InvalidRequestParameterException {
        return ResponseEntity.ok(movieConfigService.FindAllMovieConfig());
    }
}
