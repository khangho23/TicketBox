package com.example.demo.service;

import java.util.List;

public interface BaseService<T,E> {
	List<T> findAll();
}
