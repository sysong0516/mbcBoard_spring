package com.example.mbcBoard.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) // null값을 가지는 필드는 JSON 응답에 포함되지 않게
@Getter
@AllArgsConstructor
public class ResponseDTO<T> {
	
	private String success;
	private String message;
	private T data;
}
