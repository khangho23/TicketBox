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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest(classes = CustomerTestApplication.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GsonService gsonService;

	@Test
	public void findById() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findById");
		this.mockMvc.perform(get("/api/customer/1").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void findByIdIsInvalidDataType() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsInvalidDataType");
		this.mockMvc.perform(get("/api/customer/customer01").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void findByIdIsNotPresent() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNotPresent");
		this.mockMvc.perform(get("/api/customer/1000000").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void findByIdIsNull() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "findByIdIsNull");
		this.mockMvc.perform(get("/api/customer/null").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void registrationConfirm() throws Exception {
		String methodName = "registrationConfirm";
		Reader reader = Files.newBufferedReader(
				Paths.get("src/test/resources/" + this.getClass().toString().replace("class ", "").replace(".", "/")
						+ "_" + methodName + "_expect.json"));

		String expect = reader.toString();
		this.mockMvc
				.perform(get("/api/customer/active?userToken=dsfa312414safaasf")
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void registrationConfirmIsNotPresent() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "registrationConfirmIsNotPresent");
		this.mockMvc
				.perform(get("/api/customer/active?userToken=13214521$@@!")
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void registrationConfirmIsNull() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "registrationConfirmIsNull");
		this.mockMvc.perform(get("/api/customer/active").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}

	@Test
	public void registrationConfirmIsNothing() throws Exception {
		String expect = gsonService.getValueExpect(this.getClass().toString(), "registrationConfirmIsNothing");
		this.mockMvc.perform(get("/api/customer/active?userToken=").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString(expect)));
	}
}
