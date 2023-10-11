package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.Dimension;

@Dao
@ConfigAutowireable
public interface DimensionDao {
    @Select
    List<Dimension> findAll();

    @Select
    Optional<Dimension> findById(Integer id);
}
