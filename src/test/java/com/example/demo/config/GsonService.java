package com.example.demo.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Service
@Data
public class GsonService {
	private Gson gson;

	public String getValueExpect(String className, String methodName) {
		FileReader fileReader;
		String fileJson = "src/test/resources/" +
				className.replace("class ", "").replace(".", "/") +
				"_" + methodName + "_expect.json";
		try {
			fileReader = new FileReader(fileJson);
			this.gson = new Gson();
			return gson.fromJson(fileReader, JsonElement.class).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
