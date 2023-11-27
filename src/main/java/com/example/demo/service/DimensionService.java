package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.dao.DimensionDao;
import com.example.demo.entity.Dimension;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class DimensionService {
    @Autowired
    DimensionDao dimensionDao;

    public List<Dimension> findAll() {
        return dimensionDao.findAll();
    }

    public Optional<Dimension> findById(Integer id) throws InvalidRequestParameterException {
        return Optional.of(dimensionDao.findById(id)
                .orElseThrow(() -> new InvalidRequestParameterException("Dimension", RequestParameterEnum.NOT_FOUND)));
    }

}
