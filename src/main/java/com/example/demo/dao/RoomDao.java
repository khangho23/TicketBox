package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Room;

@Dao
@ConfigAutowireable
public interface RoomDao {
    @Select
    List<RoomDto> getByBranch(String id, String showdate);
}
