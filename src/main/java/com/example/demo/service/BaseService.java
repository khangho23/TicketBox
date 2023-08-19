package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.exception.InvalidRequestParameterException;

public interface BaseService<T,E> {
	List<T> findAll();
	Optional<T> findById(E id) throws InvalidRequestParameterException;
}
