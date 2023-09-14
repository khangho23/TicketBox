package com.example.demo.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	public static File multipartFileToFileConverter(String path, MultipartFile multipartFile) throws IOException {
		File file = new File(path + multipartFile.getOriginalFilename());
		System.out.print(multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		return file;
	}

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
