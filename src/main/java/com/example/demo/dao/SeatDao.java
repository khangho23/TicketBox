package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.SeatDto;
import com.example.demo.entity.Seat;

@Dao
@ConfigAutowireable
public interface SeatDao {
    @Select
    List<Seat> findAll();
    @Select
    List<SeatDto> findByRoomId(String id);
}
