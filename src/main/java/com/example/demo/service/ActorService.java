package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ActorDao;
import com.example.demo.entity.Actor;

@Service
public class ActorService {
	@Autowired
	private ActorDao actorDao;

	public List<Actor> findAll() {
		return actorDao.findAll();
	}
}
