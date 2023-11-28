package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MovieConfigDao;
import com.example.demo.dto.MovieConfigDto;
import com.example.demo.entity.MovieConfig;
import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.enums.RequestStatusEnum;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class MovieConfigService {
	@Autowired
	private MovieConfigDao movieConfigDao;

	public RequestStatusEnum update(MovieConfig movieConfig) throws InvalidRequestParameterException {
		if (!movieConfigDao.findByMovieId(movieConfig.getMovieId(), movieConfig.getBranchId()).isPresent()) {
			return movieConfigDao.insert(movieConfig) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE;
		} else {
			return (movieConfigDao.update(movieConfig)) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE;
		}
	}

	public List<MovieConfig> findAllByMovieId(String moviveId) throws InvalidRequestParameterException {
		List<MovieConfig> list = movieConfigDao.findAllByMovieId(moviveId);
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("MovieConfig", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<MovieConfigDto> FindAllMovieConfig() throws InvalidRequestParameterException {
		List<MovieConfigDto> listMovie = movieConfigDao.findAll();
		for (MovieConfigDto movieConfig : listMovie) {
			List<MovieConfig> MovieConfigById = movieConfigDao.findAllByMovieId(movieConfig.getId());
			movieConfig.setListMovieConfig(MovieConfigById);
		}

		return listMovie;
	};
}
