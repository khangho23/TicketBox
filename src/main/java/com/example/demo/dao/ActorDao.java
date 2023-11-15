package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.Actor;

@Dao
@ConfigAutowireable
public interface ActorDao {
	@Select
	List<Actor> findAll();

	@Select
	Actor findById(int id);
	
	@Select
	Optional<Actor> findByName(String name);
}
