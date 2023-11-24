package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.dao.TypeOfMovieDao;
import com.example.demo.entity.TypeOfMovie;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class TypeOfMovieService {
    @Autowired
    TypeOfMovieDao typeOfMovieDao;

    public List<TypeOfMovie> findAll() {
        return typeOfMovieDao.findAll();
    }

    public TypeOfMovie findById(String id) throws InvalidRequestParameterException {
        return typeOfMovieDao.findById(id)
                            .orElseThrow(()->new InvalidRequestParameterException("Phim",RequestParameterEnum.NOT_FOUND));
    }
}
