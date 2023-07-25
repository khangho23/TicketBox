package com.example.demo.service;

import com.example.demo.dao.MovieTypeDao;
import com.example.demo.entity.MovieType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieTypeService {
    @Autowired
    MovieTypeDao movieTypeDao;

    public List<MovieType> findAll() {
        return movieTypeDao.findAll();
    }

    public MovieType findById(String id) {
        return movieTypeDao.findById(id);
    }
}
