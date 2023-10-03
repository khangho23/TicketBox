package com.example.demo.controller;

import com.example.demo.CustomerTestApplication;
import com.example.demo.config.GsonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = CustomerTestApplication.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GsonService gsonService;

	private final String URL = "/api/customer";

	// Expect result
	private String expect;

	@Test
	public void findById() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "findById");
		this.mockMvc.perform(get(URL + "/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void findByIdIsInvalidDataType() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsInvalidDataType");
		this.mockMvc.perform(get(URL + "/{id}", "customer01").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void findByIdIsNotPresent() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNotPresent");
		this.mockMvc.perform(get(URL + "/{id}", 1000000).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void findByIdIsNull() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNull");
		this.mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void registrationConfirmIsNotPresent() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "registrationConfirmIsNotPresent");
		this.mockMvc
				.perform(get(URL + "/active").param("userToken", "13214521$@@!")
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void registrationConfirmIsNull() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "registrationConfirmIsNull");
		this.mockMvc.perform(get(URL + "/active").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void registrationConfirmIsNothing() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "registrationConfirmIsNothing");
		this.mockMvc.perform(get(URL + "/active").param("userToken", "").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}
}
