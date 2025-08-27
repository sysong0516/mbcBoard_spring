package com.example.mbcBoard.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가지는 필드는 JSON 응답에 포함되지 않음
@Getter
@AllArgsConstructor
public class ResponseMessageDTO<T> {

	private String success;
	private String message; 
	private T data;
	
}
