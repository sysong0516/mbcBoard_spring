package com.example.mbcBoard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {

	private int status; // 상태코드
	private T data; // 응답 데이터
	
}
