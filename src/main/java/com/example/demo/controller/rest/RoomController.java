package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.RoomService;

@RestController
@RequestMapping("/api/room")
@CrossOrigin("*")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping({ "/", "" })
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) throws InvalidRequestParameterException {
        return ResponseEntity.ok(roomService.findById(id)
                .orElseThrow(() -> new InvalidRequestParameterException("Room", RequestParameterEnum.NOT_EXISTS)));
    }

    @GetMapping("/getRoomWithFilm")
    public ResponseEntity<?> getByBranch(@RequestParam("id") String id, @RequestParam("showdate") String showdate) {
        return ResponseEntity.ok(roomService.getByBranch(id, showdate));
    }
}
