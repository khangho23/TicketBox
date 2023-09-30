package com.example.demo.model;

import lombok.Data;

@Data
public class FileInfo {
	private String fileName;
	private Long fileLength;
	private String contentType;
	private Boolean isReadable;
	private Boolean isFileEmpty;
	private byte[] fileData;
}
