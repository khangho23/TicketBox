package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.admin.controller.enums.RequestStatusEnum;
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
import com.example.demo.dto.requestMovieDto;
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
import com.example.demo.util.FileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

	@Autowired
	DataSource dataSource;

	@Autowired
	S3Service s3Service;

	// The name of an existing bucket, or access point ARN, to which the new object
	// will be uploaded
	final String BUCKET_NAME = "zuhot-cinema-images";

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
		return movies;
	}

	public String insertMovie(requestMovieDto movie, MultipartFile multipartFile)
			throws InvalidRequestParameterException, SQLException, IOException {
		// Kiểm tra movieId tồn tại chưa
		Optional<Movie> movieById = movieDao.findById(movie.getId());
		if (!movieById.isPresent()) {
			String folder = "poster-movie/";
			String extension = FileUtils.getExtension(multipartFile.getOriginalFilename());
			String fileName = movie.getId();
			String key = folder + fileName + "." + extension;

			InputStream inputStream = multipartFile.getInputStream();
			// Đặt content-type cho Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType("image/" + extension);

			// Lưu movie poster tới S3 bucket
			s3Service.saveFile(BUCKET_NAME, key, inputStream, objectMetadata);
			// Cập nhật movie poster 
			movie.setPoster(movie.getId() + "." + extension);

			ObjectMapper objectMapper = new ObjectMapper();
	        String json;
			Connection connection = dataSource.getConnection();
			// Chuyển đổi thành java.sql.Array(tương thích với biến truyền vào ở function
			// sql)
			movie.setLanguage2(""+connection.createArrayOf("integer", movie.getArrayLanguage().toArray()));
			movie.setType2(""+connection.createArrayOf("text", movie.getArrayType().toArray()));
			movie.setActor2(""+connection.createArrayOf("text", movie.getArrayActor().toArray()));
			movie.setDirector2(""+connection.createArrayOf("text", movie.getArrayDirector().toArray()));
			json = objectMapper.writeValueAsString(movie);
			movieDao.insertmovie(json);
			return RequestStatusEnum.SUCCESS.name();
		}
		throw new InvalidRequestParameterException("Duplicate key", RequestParameterEnum.EXISTS);
	}

	public String updateMovie(requestMovieDto movie, MultipartFile multipartFile)
			throws InvalidRequestParameterException, SQLException, IOException {
//		Kiểm tra movieId tồn tại chưa
		Optional<Movie> movieById = movieDao.findById(movie.getId());
		if (movieById.isPresent()) {
			String fileNameExists;

			String folder = "poster-movie/";
			String extension = FileUtils.getExtension(multipartFile.getOriginalFilename());
			String fileName = movie.getId();
			String key = folder + fileName + "." + extension;

			InputStream inputStream = multipartFile.getInputStream();
			// Đặt content-type cho Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType("image/" + extension);

			// Kiểm tra movie poster đã tồn tại trên aws
			if (movieById.get().getPoster() != null) {
				String poster = movieById.get().getPoster();
				fileNameExists = poster.substring(0, movieById.get().getPoster().indexOf("."));

				if (fileNameExists.equals(fileName))
					s3Service.deleteFile(BUCKET_NAME, folder + poster);
			}
			// Lưu movie poster tới S3 bucket
			s3Service.saveFile(BUCKET_NAME, key, inputStream, objectMetadata);

			// Cập nhật movie poster 
			movie.setPoster(movie.getId() + "." + extension);
			
			ObjectMapper objectMapper = new ObjectMapper();
	        String json;
			Connection connection = dataSource.getConnection();
			// Chuyển đổi thành java.sql.Array(tương thích với biến truyền vào ở function
			// sql)
			movie.setLanguage2(""+connection.createArrayOf("integer", movie.getArrayLanguage().toArray()));
			movie.setType2(""+connection.createArrayOf("text", movie.getArrayType().toArray()));
			movie.setActor2(""+connection.createArrayOf("text", movie.getArrayActor().toArray()));
			movie.setDirector2(""+connection.createArrayOf("text", movie.getArrayDirector().toArray()));
			json = objectMapper.writeValueAsString(movie);
			movieDao.updatemovie(json);
			return RequestStatusEnum.SUCCESS.name();
		}
		throw new InvalidRequestParameterException("Key does not exist", RequestParameterEnum.NOT_EXISTS);
	}
	
}
