package com.example.demo.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	public static String getExtension(String originalFilename) {
		// Extract the extension from the file name
		int index = originalFilename.lastIndexOf('.');
		if (index > 0) {
			String extension = originalFilename.substring(index + 1);
			return extension;
		}

		return null;
	}
}
