package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SeatDao;
import com.example.demo.dto.SeatDto;
import com.example.demo.model.SeatModel;

@Service
public class SeatService {
    @Autowired
    private SeatDao seatDao;

    /**
     * Get seat by roomid
     * 
     * @param id
     * @return SeatHasCheckTicket
     */
    public List<SeatDto> findByRoomId(String id){
        return seatDao.findByRoomId(id);
    }

    /**
     * Get seat orderd or not
     * 
     * @param id
     * @return SeatModel
     */
    public SeatModel getSeatHasCheckTicket(int id){
        return new SeatModel(seatDao.getSeatHasCheckTicket(id));
    }
}
