package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ActorService;

@RestController
@RequestMapping("/api/actor")
@CrossOrigin("*")
public class ActorController {

	@Autowired
	private ActorService actorService;
	
	@GetMapping({"","/"})
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(actorService.findAll());
	}
}
