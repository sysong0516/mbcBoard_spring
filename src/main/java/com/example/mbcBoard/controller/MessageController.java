package com.example.mbcBoard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.domain.MessageDTO;
import com.example.mbcBoard.domain.ResponseMessageDTO;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.repository.UserRepository;
import com.example.mbcBoard.service.MessageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {

	private final MessageService messageService;
	private final UserRepository userRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/message")
	public ResponseMessageDTO<?> sendMessage(@RequestBody MessageDTO messageDTO){
		// 임의로 유저 정보를 넣음, JWT 도입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		User user = userRepository.findById(2).orElseThrow(()->{
			return new IllegalArgumentException("유저를 찾을 수 없습니다.");
		});
		messageDTO.setSenderName(user.getUsername());
		
		return new ResponseMessageDTO<>("성공","쪽지를 보냈습니다.",messageService.write(messageDTO));
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/messages/received")
	public ResponseMessageDTO<?> getReceivedMessage(){
		// 임의로 유저 정보를 넣었지만, JWT 고입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		User user = userRepository.findById(3).orElseThrow(()->{
			return new IllegalArgumentException("유저를 찾을 수 없습니다.");
		});
		
		return new ResponseMessageDTO<>("성공","받은 쪽지를 불러왔습니다.",messageService.receivedMessage(user));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/messages/received/{id}")
	public ResponseMessageDTO<?> deleteReceiverMessage(@PathVariable("id") Integer id){
		// 임의로 유저 정보를 넣었지만, JWT 고입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		User user = userRepository.findById(1).orElseThrow(()->{
			return new IllegalArgumentException("유저를 찾을 수 없습니다.");
		});
		
		return new ResponseMessageDTO<>("삭제 성공","받은 쪽지인,"+id+"번 쪽지를 삭제했습니다.",messageService.deleteMessageByReceiver(id, user));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/messages/sent")
	public ResponseMessageDTO<?> getSentMessage(){
		// 임의로 유저 정보를 넣었지만, JWT 고입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		User user = userRepository.findById(1).orElseThrow(()->{
			return new IllegalArgumentException("유저를 찾을 수 없습니다.");
		});
		return new ResponseMessageDTO<>("성공", "보낸 쪽지를 불러왔습니다.", messageService.sentMessage(user));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/messages/sent/{id}")
	public ResponseMessageDTO<?> deleteSentMessage(@PathVariable("id") Integer id){
		// 임의로 유저 정보를 넣었지만, JWT 고입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		User user = userRepository.findById(1).orElseThrow(()->{
			return new IllegalArgumentException("유저를 찾을 수 없습니다.");
		});
		return new ResponseMessageDTO<>("삭제 성공","보낸 쪽지인,"+id+"번 쪽지를 삭제했습니다.",messageService.deleteMessageBySender(id, user));
	}
}
