package com.example.demo.dao;

import com.example.demo.entity.MovieType;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface MovieTypeDao {
    @Select
    List<MovieType> findAll();

    @Select
    MovieType findById(String id);
}
