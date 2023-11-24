package com.example.demo.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoomDao;
import com.example.demo.dto.RoomDto;
import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class RoomService{
    @Autowired
    RoomDao roomDao;

    public List<RoomDto> findAll() {
        return roomDao.findAll();
    }

    public Optional<RoomDto> findById(String id) {
        return roomDao.findById(id);
    }

    public List<RoomDto> getByBranch(String id, String showdate) {
        return roomDao.getByBranch(id, showdate);
    }
    
    public List<RoomDto> findByBranchId(Optional<String> branchid) throws InvalidRequestParameterException {
    	if (branchid == null) throw new InvalidRequestParameterException("Room branchid", RequestParameterEnum.NOT_EXISTS);
    	branchid.orElseThrow(() -> new InvalidRequestParameterException("Room branchid", RequestParameterEnum.NOTHING));
    	List<RoomDto> room = roomDao.findByBranchId(branchid.get());
    	if (room.isEmpty()) throw new InvalidRequestParameterException("Room", RequestParameterEnum.NOT_FOUND);
    	
    	return room;
    }
}
