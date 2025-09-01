package com.example.mbcBoard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.domain.MessageDTO;
import com.example.mbcBoard.domain.ResponseMessageDTO;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.domain.UserDTO;
import com.example.mbcBoard.repository.MessageRepository;
import com.example.mbcBoard.repository.UserRepository;
import com.example.mbcBoard.security.SecurityUtil;
import com.example.mbcBoard.security.UserDetailsImpl;
import com.example.mbcBoard.service.MessageService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {


	private final MessageService messageService;
	private final UserRepository userRepository;
	private final SecurityUtil securityUtil;

	@ResponseStatus(HttpStatus.CREATED) //성공 시 200번 보다는 201번이 더 적절
	@PostMapping("/message")
	public ResponseMessageDTO<?> sendMessage(@RequestBody MessageDTO messageDTO, Authentication auth){
		// 임의로 유저 정보를 넣음, JWT 도입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		UserDTO dto = securityUtil.getCurrentUser(auth);
		System.out.println(dto);
		User sender = userRepository.findById(dto.getId()).get();
		 
		messageDTO.setSender(sender);
		
		
		System.out.println(sender);
		return new ResponseMessageDTO<>("성공", "쪽지를 보냈습니다.", messageService.write(messageDTO)); 
	
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/messages/received")
	public ResponseEntity<?> getReceivedMessage(Authentication auth){
		// 임의로 유저 정보를 넣었지만, JWT 고입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		UserDTO dto = securityUtil.getCurrentUser(auth);
		
		User receiver = userRepository.findById(dto.getId()).get();
		
		
		return new ResponseEntity<>(messageService.receivedMessage(receiver),HttpStatus.OK);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/messages/sent")
	public ResponseEntity<?> getSentMessage(Authentication auth){
		// 임의로 유저 정보를 넣었지만, JWT 고입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함
		UserDTO dto = securityUtil.getCurrentUser(auth);
		
		User sender = userRepository.findById(dto.getId()).get();
		
		return new ResponseEntity<>(messageService.sentMessage(sender),HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/messages/delete/{who}")
	public ResponseEntity<?> deleteByMessage(@RequestBody List<Integer> id,@PathVariable String who, Authentication auth ){
		// 임의로 유저 정보를 넣었지만, JWT 고입하고 현재 로그인 된 유저의 정보를 넘겨줘야 함 
		// 컨트롤러로 id 받는 것 까지 함 
		
		for(Integer i:id) {
			messageService.deleteMessageByTarget(i,who);			
		}
		
		
		return new ResponseEntity<>("삭제 성공",HttpStatus.OK);
	}
}
