package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MovieService;
import com.example.demo.service.SeatService;

@RestController
@RequestMapping("/api/seat")
@CrossOrigin("*")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Autowired
    private MovieService movieService;

    @GetMapping(value ={"/",""})
    public ResponseEntity<?> findByRoomId(@RequestParam("roomid") String roomId){
        return ResponseEntity.ok(seatService.findByRoomId(roomId));
    }

    @GetMapping("/getSeatHasCheckTicket")
    public ResponseEntity<?> getSeatHasCheckTicket(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<>();
        map.put("seat",seatService.getSeatHasCheckTicket(id));
        map.put("movie",movieService.findByShowTimeId(id));
        return ResponseEntity.ok(map);
    }
}
