package com.example.mbcBoard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
	private int id;
	private String content;
	private String writer;
	
	public static ReplyDTO toDto(Reply reply) {
		return new ReplyDTO(
				reply.getId(),
				reply.getContent(),
				reply.getUser().getUsername()
		);
				
	}
}
