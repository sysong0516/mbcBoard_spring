package com.example.mbcBoard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private int id; // 회원번호
	private String username; //유저이름
	
	public UserDTO(User user) {
		this.id=user.getId();
		this.username=user.getUsername();
	}
}
