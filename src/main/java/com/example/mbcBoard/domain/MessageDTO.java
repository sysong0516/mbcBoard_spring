package com.example.mbcBoard.domain;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

	private int id;
	private String content;
	private User sender;
	private String receiverName;
	
	public static MessageDTO toDTO(Message message) {
		// MessageDTO에 Message 엔티티를 참조 한 title,content 등 반환(이해 필요)
		return new MessageDTO(
				message.getId(),
				message.getContent(),
				message.getSender(),
				message.getReceiver().getUsername()
		);
	}
}
