package com.example.demo.controller;

import com.example.demo.CustomerTestApplication;
import com.example.demo.config.GsonService;
import com.example.demo.controller.rest.CustomerController;
import com.example.demo.dao.CustomerDao;
import com.example.demo.dao.CustomerDaoImpl;
import com.example.demo.service.CustomerService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CustomerTestApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {
    private final String URL = "/api/customer";

    @Mock
    MockMvc mockMvc;

    // Expect result
    private String expect;

    @Mock
    private GsonService gsonService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() throws Exception {
		expect = gsonService.getValueExpect(this.getClass().toString(), "testFindById");
		this.mockMvc.perform(get(URL + "/{id}", 2).accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(expect))).andReturn();
    }

    @Test
    public void testFindByIdIsInvalidDataType() throws Exception {
        expect = gsonService.getValueExpect(this.getClass().toString(), "testFindByIdIsInvalidDataType");
        this.mockMvc.perform(get(URL + "/{id}", "customer01").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expect)));
    }

    @Test
    public void testFindByIdIsNotPresent() throws Exception {
        expect = gsonService.getValueExpect(this.getClass().toString(), "testFindByIdIsNotPresent");
        this.mockMvc.perform(get(URL + "/{id}", 1000000).accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expect)));
    }

    @Test
    public void testFindByIdIsNull() throws Exception {
        expect = gsonService.getValueExpect(this.getClass().toString(), "testFindByIdIsNull");
        this.mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expect)));
    }

    @Test
    public void testRegistrationConfirmIsNotPresent() throws Exception {
        expect = gsonService.getValueExpect(this.getClass().toString(), "testRegistrationConfirmIsNotPresent");
        this.mockMvc
                .perform(get(URL + "/active").param("userToken", "13214521$@@!")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expect)));
    }

    @Test
    public void testRegistrationConfirmIsNull() throws Exception {
        expect = gsonService.getValueExpect(this.getClass().toString(), "testRegistrationConfirmIsNull");
        this.mockMvc.perform(get(URL + "/active").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expect)));
    }

    @Test
    public void testRegistrationConfirmIsNothing() throws Exception {
        expect = gsonService.getValueExpect(this.getClass().toString(), "testRegistrationConfirmIsNothing");
        this.mockMvc.perform(get(URL + "/active").param("userToken", "").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expect)));
    }
}
