package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.dao.ActorDao;
import com.example.demo.dao.ActorOfMovieDao;
import com.example.demo.dao.DirectorDao;
import com.example.demo.dao.DirectorOfMovieDao;
import com.example.demo.dao.LanguageDao;
import com.example.demo.dao.LanguageOfMovieDao;
import com.example.demo.dao.MovieDao;
import com.example.demo.dao.MovieDetailsDao;
import com.example.demo.dao.TypeOfMovieDao;
import com.example.demo.dto.MovieDto;
import com.example.demo.entity.Actor;
import com.example.demo.entity.ActorOfMovie;
import com.example.demo.entity.Director;
import com.example.demo.entity.DirectorOfMovie;
import com.example.demo.entity.Language;
import com.example.demo.entity.LanguageOfMovie;
import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieDetails;
import com.example.demo.entity.TypeOfMovie;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.MovieDetailModel;

@Service
public class MovieService implements BaseService<Movie, String> {

	@Autowired
	private MovieDao movieDao;

	@Autowired
	private LanguageOfMovieDao languageOfMovieDao;

	@Autowired
	private LanguageDao languageDao;

	@Autowired
	private TypeOfMovieDao typeOfMovieDao;

	@Autowired
	private MovieDetailsDao movieDetailsDao;

	@Autowired
	private ActorDao actorDao;

	@Autowired
	private ActorOfMovieDao actorOfMovieDao;

	@Autowired
	private DirectorDao directorDao;

	@Autowired
	private DirectorOfMovieDao directorOfMovieDao;

	@Override
	public List<Movie> findAll() {
		// TODO Auto-generated method stub
		return movieDao.findAll();
	}

	@Override
	public Optional<Movie> findById(String id) throws InvalidRequestParameterException {
		return Optional.of(movieDao.findById(id)
				.orElseThrow(() -> new InvalidRequestParameterException("Phim", RequestParameterEnum.NOT_FOUND)));
	}

	public List<Movie> findByStatus(String status) throws InvalidRequestParameterException {
		// TODO Auto-generated method stub
		List<Movie> list = movieDao.findByStatus(status);
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("Phim", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<Movie> findMoviesNowShowing() {
		// TODO Auto-generated method stub
		return movieDao.findMoviesNowShowing();
	}

	public MovieDetailModel findMovieDetailPage(String movieId) {
		MovieDto movieDto = movieDao.findMovieDetailPage(movieId);
		return new MovieDetailModel(movieDto, movieDao.findByTypeOfMovieId(movieDto.getMovieTypeId().split(",")));
	}

	public MovieDto findByShowTimeId(int showTimeId) {
		return movieDao.findByShowTimeId(showTimeId);
	}

	public List<Movie> findMovieHomePage(String branchid, int countryid, String typeofmovieid, String status)
			throws InvalidRequestParameterException {
		String branch = branchid.isEmpty() ? null : branchid;
		String movieType = typeofmovieid.isEmpty() ? null : typeofmovieid;
		List<Movie> list = movieDao.findMovieHomePage(branch, countryid, movieType, status);
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("Phim", RequestParameterEnum.NOT_FOUND);
		}
		return list;

	}

	public List<Movie> findByName(String name) throws InvalidRequestParameterException {
		List<Movie> list = movieDao.findByName("%" + name.toLowerCase() + "%");
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("Phim", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}
	// ADMIN

	public Optional<Movie> findMovieById(String movieId) {
		// List Languages
		Optional<Movie> movie = movieDao.findById(movieId);
		List<Language> languages = new ArrayList<>();
		List<LanguageOfMovie> listLanguageOfMovies = languageOfMovieDao.findByMovieId(movieId);
		for (LanguageOfMovie languageOfMovie : listLanguageOfMovies) {
			Language language = languageDao.findById(languageOfMovie.getLanguageId());
			languages.add(language);
		}
		movie.get().setLanguage(languages);
		// List Types
		List<TypeOfMovie> types = new ArrayList<>();
		List<MovieDetails> listTypeOfMovies = movieDetailsDao.findByMovieId(movieId);
		for (MovieDetails typeOfMovie : listTypeOfMovies) {
			Optional<TypeOfMovie> type = typeOfMovieDao.findById(typeOfMovie.getTypeOfMovieId());
			types.add(type.get());
		}
		movie.get().setType(types);
		// List Actors
		List<Actor> actors = new ArrayList<>();
		List<ActorOfMovie> listActorOfMovie = actorOfMovieDao.findByMovieId(movieId);
		for (ActorOfMovie actorOfMovie : listActorOfMovie) {
			Actor actor = actorDao.findById(actorOfMovie.getActorId());
			actors.add(actor);
		}
		movie.get().setActor(actors);
		// List Directors
		List<Director> directors = new ArrayList<>();
		List<DirectorOfMovie> listDirectorOfMovie = directorOfMovieDao.findByMovieId(movieId);
		for (DirectorOfMovie directorOfMovie : listDirectorOfMovie) {
			Director director = directorDao.findById(directorOfMovie.getDirectorId());
			directors.add(director);
		}
		movie.get().setDirector(directors);
		
		return movie;

	}

	public List<Movie> findAllMovieAdmin() {
		List<Movie> movies = movieDao.findAllMovieAmin();

		for (Movie movie : movies) {
			// List Language
			List<LanguageOfMovie> listLanguageOfMovie = languageOfMovieDao.findByMovieId(movie.getId());
			List<Language> languages = new ArrayList<>();
			for (LanguageOfMovie languageOfMovie : listLanguageOfMovie) {
				Language language = languageDao.findById(languageOfMovie.getLanguageId());
				languages.add(language);
			}
			movie.setLanguage(languages);
		}
		return movies;

	}
}
