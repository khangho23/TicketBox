package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.enums.RequestStatusEnum;
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
    
    public List<LanguageOfMovieDto> findByMovieConfig(Optional<String> branchId) throws InvalidRequestParameterException {
    	branchId.orElseThrow(() -> new InvalidRequestParameterException("LanguageOfMovieService branchId", RequestParameterEnum.NOTHING));
    	List<LanguageOfMovieDto> languageOfMovie = languageOfMovieDao.findByMovieConfig(branchId.get());
    	if (languageOfMovie.isEmpty()) 
    		throw new InvalidRequestParameterException("LanguageOfMovieService", RequestParameterEnum.NOT_FOUND);
    	
    	return languageOfMovie;
    }
}
