package com.example.demo.controller;

import com.example.demo.MovieTestApplication;
import com.example.demo.config.GsonService;
import com.example.demo.exception.InvalidRequestParameterException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = MovieTestApplication.class)
@AutoConfigureMockMvc
public class MovieControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GsonService gsonService;

	@Test
	public void findById() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findById");
		this.mockMvc.perform(get("/api/movie/MP01").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void finByIdIsNotPresent() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNotPresent");
			this.mockMvc.perform(get("/api/movie/12").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andExpect(content().string(containsString(expect.toString())));
		}
	
	@Test
	public void findByIdIsNull() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNull");
		this.mockMvc.perform(get("/api/movie/").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}

	@Test
	public void findAllWithStatus() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findAllWithStatus");
		this.mockMvc.perform(get("/api/movie?status=1").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void findAllWithStatusIsWrong() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNotPresent");
		this.mockMvc.perform(get("/api/movie?status='a'").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void findMovieDetailPageWithId() throws Exception{
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findMovieDetailPage");
		this.mockMvc.perform(get("/api/movie/getDetail?id=MP01").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void findMovieDetailPageWithIdNotPresent() throws Exception{
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNotPresent");
		this.mockMvc.perform(get("/api/movie/getDetail?id=MP51").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void findMoviesNowShowing() throws Exception{
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findMoviesNowShowing");
		this.mockMvc.perform(get("/api/movie/nowshowing").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void getByShowTime() throws Exception{
		String expect = gsonService.getValueExpect(this.getClass().toString(), "getByShowTime");
		this.mockMvc.perform(get("/api/movie/getByShowTime?showtimeid=1").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void getByShowTimeWithIdisNull() throws Exception{
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNotPresent");
		this.mockMvc.perform(get("/api/movie/getByShowTime?showtimeid=").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	@Test
	public void getByShowTimeNotId() throws Exception{
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNotPresent");
		this.mockMvc.perform(get("/api/movie/getByShowTime").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect.toString())));
	}
	
	
}
