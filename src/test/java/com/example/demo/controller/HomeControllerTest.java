package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.example.demo.controller.rest.CustomerController;
import com.example.demo.controller.rest.MovieController;
import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.MovieTestApplication;
import com.example.demo.config.GsonService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootTest(classes = MovieTestApplication.class)
@AutoConfigureMockMvc
public class HomeControllerTest {
//	private MockMvc mockMvc;

	@InjectMocks
	GsonService gsonService;

	@InjectMocks
	Movie movie;

	@Mock
	MovieController movieController;

	@Mock
	MovieService movieService;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeTest
	public void setup() {
		MockitoAnnotations.openMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(this.movieController).build();
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getAll() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(),"getAll");
//		System.out.println(movieController.findAll().toString());
//		mockMvc.perform(get("/api/movie")
//							.accept(MediaType.APPLICATION_JSON_VALUE))
//					.andExpect(content().string(containsString(expect)));

		List<Movie> movies = gsonService.fromJson(expect, List.class);
//		List<Movie> movieController2 = gsonService.fromJson(movieController.findAll(), List.class);
		Mockito.when(movieService.findAll()).thenReturn(movies);
	}
}
