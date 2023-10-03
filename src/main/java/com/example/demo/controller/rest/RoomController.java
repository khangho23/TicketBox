package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RoomService;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/getRoomWithFilm")
    public ResponseEntity<?> getByBranch(@RequestParam("id") String id, @RequestParam("showdate") String showdate){
        return ResponseEntity.ok(roomService.getByBranch(id,showdate));
    }
}
