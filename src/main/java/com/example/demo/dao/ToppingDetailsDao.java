package com.example.demo.dao;

import com.example.demo.entity.ToppingDetails;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface ToppingDetailsDao {
    @Insert
    int insert(ToppingDetails toppingDetails);
}
