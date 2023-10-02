package com.example.demo.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Service
public class GsonService {
    private Gson gson;

    public String getValueExpect(String className, String methodName) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get("src/test/resources/" + className.replace("class ", "").replace(".", "/") + "_" + methodName + "_expect.json"));
            this.gson = new Gson();
            return gson.fromJson(reader, JsonElement.class).toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}