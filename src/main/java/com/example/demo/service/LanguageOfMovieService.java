package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LanguageOfMovieDao;
import com.example.demo.dto.LanguageOfMovieDto;

@Service
public class LanguageOfMovieService {
    @Autowired
    LanguageOfMovieDao languageOfMovieDao;

    public List<LanguageOfMovieDto> findAll() {
        return languageOfMovieDao.findAll();
    }
}
