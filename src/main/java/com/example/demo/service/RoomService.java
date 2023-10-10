package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoomDao;
import com.example.demo.dto.RoomDto;

@Service
public class RoomService {
    @Autowired
    private RoomDao roomDao;

    public List<RoomDto> getByBranch(String id, String showdate){
        return roomDao.getByBranch(id,showdate);
    }
}
