package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.dao.ActorDao;
import com.example.demo.entity.Actor;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class ActorService {
	@Autowired
	private ActorDao actorDao;

	public List<Actor> findAll() {
		return actorDao.findAll();
	}

	public RequestStatusEnum insert(Actor actor) throws InvalidRequestParameterException {
		return actorDao.insert(actor) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE;
	}

}
