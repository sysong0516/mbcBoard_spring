package com.example.mbcBoard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mbcBoard.domain.Message;
import com.example.mbcBoard.domain.MessageDTO;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.repository.MessageRepository;
import com.example.mbcBoard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final필드나 nonnull필드만을 포함한 생성자를 자동으로 생성
@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	
	@Transactional // 데이터 베이스 작업을 하나의 작업 단위로 묶움
	public MessageDTO write (MessageDTO messageDTO) {
		// 흐름 파악 필요 + 이해 필요
		User receiver = userRepository.findByUsername(messageDTO.getReceiverName())
				.orElseThrow(()-> new IllegalArgumentException("수신자 유저를 찾을 수 없습니다."));
//		User sender = userRepository.findByUsername(messageDTO.getSenderName())
//				.orElseThrow(() -> new IllegalArgumentException("발신자 유저를 찾을 수 없습니다."));
		
		Message message = new Message(); // 객체를 만들고 변수에 저장
		message.setReceiver(receiver); 
		message.setSender(messageDTO.getSender());
		
		message.setContent(messageDTO.getContent());
		message.setDeletedByReceiver(false);
		message.setDeletedBySender(false);
		messageRepository.save(message); // 저장
		
		return MessageDTO.toDTO(message); // 인터페이스 toDTO에 만들어 놓은 걸 리턴
	}
	
	@Transactional(readOnly = true)
	public MessageDTO findMeassageById(int id) {
		Message message = messageRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("메세지를 찾을 수 없습니다.");
		});
		return MessageDTO.toDTO(message);
	}
	
	@Transactional(readOnly = true) //읽기 전용
	public List<MessageDTO> receivedMessage(User user){
		// 받은 편지함 불러오기
		// 한 명의 유저가 받은 모든 메세지
		// 추후 jwt를 이용해서 재구현 예정
		List<Message> messages = messageRepository.findAllByReceiver(user); // 이해필요
		List<MessageDTO> messageDTOs = new ArrayList<>();
		
		for(Message message : messages) {
			// message에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
			if(!message.isDeletedByReceiver()) {
				messageDTOs.add(MessageDTO.toDTO(message)); // 이해 필요
			}
		}
		return messageDTOs;
	}
	
	// 받은 편지 삭제
	@Transactional
	public Object deleteMessageByReceiver(Integer id, User user) {
		Message message = messageRepository.findById(id).get();
		message.deleteByReceiver(); // 받은 사람에게 메세지 삭제
		if(message.isDeleted()) {
			//받은 사람과 보낸 사람 모두 삭제했으면, 데이터 베이스에서 삭제 요청
			messageRepository.delete(message);
			return "양쪽 모두 삭제";
		}
		return "한쪽만 삭제";
	}
	
	@Transactional(readOnly = true)
	public List<MessageDTO> sentMessage(User user){
		// 보낸 편지함 불러오기
		// 한 명의 유저가 받은 모든 메세지
		// 추후 JWT를 이용해서 재구현 예정
		List<Message> messages = messageRepository.findAllBySender(user);
		List<MessageDTO> messageDTOs = new ArrayList<>(); // 이해 필요
		
		for(Message message : messages) {
			// message에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
			if(!message.isDeletedBySender()) {
				messageDTOs.add(MessageDTO.toDTO(message));
			}
		}
		return messageDTOs;
	}
	
	// 보낸 편지 삭제
	@Transactional
	public Object deleteMessageBySender(Integer id, User user) {
		Message message = messageRepository.findById(id).get();
		message.deleteBySender(); // 받은 사람에게 메세지 삭제
		
			if(message.isDeleted()) {
				// 받은 사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제 요청
				messageRepository.delete(message);
				return "양쪽 모두 삭제";
			}
			return "한 쪽만 삭제";
		}
	
	
}
