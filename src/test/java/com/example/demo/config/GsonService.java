package com.example.demo.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Service
@Data
public class GsonService {
	private Gson gson;

	public String getValueExpect(String className, String methodName) {
		Reader reader;
		try {
			reader = Files.newBufferedReader(Paths.get("src/test/resources/"+className.replace("class ", "").replace(".", "/")+"_"+methodName+"_expect.json"));
			this.gson = new Gson();
			return gson.fromJson(reader,JsonElement.class).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public <T> T fromJson(String expect, Class<T> dataTypeClass) {
		return gson.fromJson(expect, dataTypeClass);
	}
}
