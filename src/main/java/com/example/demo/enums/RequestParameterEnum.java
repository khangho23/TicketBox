package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RequestParameterEnum{
	NOTHING("RP_01"),
	INVALID_TYPE("RP_02"),
	WRONG("RP_03"),
	NOT_EXISTS("RP_04"),
	NOT_FOUND("RP_05"),
	EXISTS("RP_06");
	@Getter
	private String name;
}
