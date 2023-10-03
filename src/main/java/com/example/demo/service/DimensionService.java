package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DimensionDao;
import com.example.demo.entity.Dimension;

@Service
public class DimensionService implements BaseService<Dimension, Integer> {
    @Autowired
    DimensionDao dimensionDao;

    @Override
    public List<Dimension> findAll() {
        return dimensionDao.findAll();
    }

    @Override
    public Optional<Dimension> findById(Integer id) {
        return dimensionDao.findById(id);
    }

}
