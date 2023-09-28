package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.example.demo.MovieTestApplication;
import com.example.demo.config.GsonService;
import com.example.demo.entity.Movie;

@SpringBootTest(classes = MovieTestApplication.class)
@AutoConfigureMockMvc
public class MovieControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private GsonService gsonService;

	@Test
	public void findById() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(),"findById");
		System.out.println(expect);
		this.mockMvc.perform(get("/api/movie/MP01")
							.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andExpect(content().string(containsString(expect.toString())));
	}
}
