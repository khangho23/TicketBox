package com.example.demo.controller;

import com.example.demo.CustomerTestApplication;
import com.example.demo.config.GsonService;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = CustomerTestApplication.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {
	MockMvc mockMvc;
	GsonService gsonService;

	@BeforeTest
	public void init() {
		gsonService = new GsonService();
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(CustomerControllerTest.class).build();
	}

	private final String URL = "/api/customer";

	// Expect result
	private String expect;

	@Test
	public void testFindById() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testFindById");
		this.mockMvc.perform(get(URL + "/{id}", 2).accept(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(content().string(containsString(expect)));
	}

	@Test
	public void testFindByIdIsInvalidDataType() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testFindByIdIsInvalidDataType");
		this.mockMvc.perform(get(URL + "/{id}", "customer01").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void testFindByIdIsNotPresent() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testFindByIdIsNotPresent");
		this.mockMvc.perform(get(URL + "/{id}", 1000000).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void testFindByIdIsNull() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testFindByIdIsNull");
		this.mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void testRegistrationConfirmIsNotPresent() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testRegistrationConfirmIsNotPresent");
		this.mockMvc
				.perform(get(URL + "/active").param("userToken", "13214521$@@!")
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void testRegistrationConfirmIsNull() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testRegistrationConfirmIsNull");
		this.mockMvc.perform(get(URL + "/active").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void testRegistrationConfirmIsNothing() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testRegistrationConfirmIsNothing");
		this.mockMvc.perform(get(URL + "/active").param("userToken", "").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}
}
