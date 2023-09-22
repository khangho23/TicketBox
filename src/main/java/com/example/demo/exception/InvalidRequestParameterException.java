package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.model.ErrorResponseModel;

import lombok.Getter;
import lombok.Setter;

public class InvalidRequestParameterException extends Exception{
	@Getter
	@Setter
	protected ErrorResponseModel response;

	public InvalidRequestParameterException(RequestParameterEnum type) {
		setResponse(new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(), param, type.getName()));
	}
}
