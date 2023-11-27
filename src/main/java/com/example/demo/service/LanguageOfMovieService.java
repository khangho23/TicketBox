package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.dao.LanguageOfMovieDao;
import com.example.demo.dto.LanguageOfMovieDto;
import com.example.demo.entity.LanguageOfMovie;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class LanguageOfMovieService {
    @Autowired
    private LanguageOfMovieDao languageOfMovieDao;

    public List<LanguageOfMovieDto> findAll() {
        return languageOfMovieDao.findAll();
    }

    public List<LanguageOfMovie> findByMovieId(String MovieId) throws InvalidRequestParameterException {
    	List<LanguageOfMovie> list = languageOfMovieDao.findByMovieId(MovieId);
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("LanguageOfMovie", RequestParameterEnum.NOT_FOUND);
		}
		return list;
    }
}
