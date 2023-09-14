package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SeatDao;
import com.example.demo.dto.SeatDto;
import com.example.demo.model.RowSeatModel;
import com.example.demo.model.SeatModel;

@Service
public class SeatService {
    @Autowired
    private SeatDao seatDao;

    /**
     * @param id
     * @return
     */
    public List<SeatDto> findByRoomId(String id){
        
        // List<SeatDto> seatDto = seatDao.findByRoomId(id);
        // SeatModel seat = new SeatModel(seatDto);
        return seatDao.findByRoomId(id);
    }
}
