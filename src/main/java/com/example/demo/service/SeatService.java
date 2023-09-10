package com.example.demo.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SeatDao;
import com.example.demo.dto.SeatDto;
import com.example.demo.model.SeatModel;

@Service
public class SeatService {
    @Autowired
    private SeatDao seatDao;

    public List<SeatDto> findByRoomId(String id){
        SeatModel seat = new SeatModel();
        Stream<SeatDto> seatDto = seatDao.findByRoomId(id).stream();
        seatDto.reduce(new SeatDto(), null, null);
        return seatDao.findByRoomId(id);
    }
}
