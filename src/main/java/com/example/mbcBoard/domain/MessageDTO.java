package com.example.mbcBoard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

	private String title;
	private String content;
	private String senderName;
	private String receiverName;
	
	public static MessageDTO toDTO(Message message) {
		// MessageDTO에 Message 엔티티를 참조 한 title,content 등 반환(이해 필요)
		return new MessageDTO(
				message.getTitle(),
				message.getContent(),
				message.getSender().getUsername(),
				message.getReceiver().getUsername()
		);
	}
}
