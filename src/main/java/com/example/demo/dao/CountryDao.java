package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.Country;

@Dao
@ConfigAutowireable
public interface CountryDao {
    @Select
    List<Country> findAll();
}
