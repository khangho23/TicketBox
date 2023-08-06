package com.example.demo.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFile {
    public static Reader readFromJson(String fileName) throws IOException{
        // String fileName = "src/main/resources/students.json";
        Path path = Paths.get(fileName);
        Reader reader = Files.newBufferedReader(path,
            StandardCharsets.UTF_8);
        return reader;
    }
}
