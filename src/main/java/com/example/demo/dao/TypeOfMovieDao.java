package com.example.demo.dao;

import com.example.demo.entity.TypeOfMovie;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface TypeOfMovieDao {
    @Select
    List<TypeOfMovie> findAll();

    @Select
    TypeOfMovie findById(String id);
}
