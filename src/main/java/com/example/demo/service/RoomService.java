package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoomDao;
import com.example.demo.dto.RoomDto;

@Service
public class RoomService implements BaseService<RoomDto, String> {
    @Autowired
    RoomDao roomDao;

    @Override
    public List<RoomDto> findAll() {
        return roomDao.findAll();
    }

    @Override
    public Optional<RoomDto> findById(String id) {
        return roomDao.findById(id);
    }

    public List<RoomDto> getByBranch(String id, String showdate) {
        return roomDao.getByBranch(id, showdate);
    }
}
