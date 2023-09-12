package com.example.demo.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	public static File multipartFileToFileConverter(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }
	
	public static String getExtension(File file) {
		// Get the file name
		String fileName = file.getName();

		// Extract the extension from the file name
		int index = fileName.lastIndexOf('.');
		if (index > 0) {
		  String extension = fileName.substring(index+1);
		  return extension;
		}
		
		return null;
	}
}
