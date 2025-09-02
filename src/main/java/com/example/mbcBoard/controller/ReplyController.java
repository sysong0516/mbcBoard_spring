package com.example.mbcBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.mbcBoard.domain.Reply;
import com.example.mbcBoard.security.SecurityUtil;
import com.example.mbcBoard.service.ReplyService;

@Controller
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@PostMapping("/reply/{postId}")
	public ResponseEntity<?> insertReply(@PathVariable int postId, @RequestBody Reply reply, Authentication auth){
		
		int userId=securityUtil.getCurrentUser(auth).getId();
		replyService.insertReply(reply, postId, userId);
		
		return new ResponseEntity<>("등록완료", HttpStatus.OK);
	}
	
	@PutMapping("/reply/{postId}")
	public ResponseEntity<?> updateReply(@RequestBody Reply reply, @PathVariable int postId, Authentication auth){
		
		int userId=securityUtil.getCurrentUser(auth).getId();
		
		if(!replyService.authUser(reply.getId(), userId)) {
			return new ResponseEntity<>("작성자만 수정할수있습니다", HttpStatus.BAD_REQUEST);
		}
		replyService.insertReply(reply, postId, userId);
		
		return new ResponseEntity<>("수정완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/reply/{id}")
	public ResponseEntity<?> deleteReply(@PathVariable Integer id, Authentication auth){
		
		int userId=securityUtil.getCurrentUser(auth).getId();
		if(!replyService.authUser(id, userId)) {
			return new ResponseEntity<>("작성자만 삭제할수있습니다", HttpStatus.BAD_REQUEST);
		}
		replyService.deleteReply(id);
		
		return new ResponseEntity<>("삭제완료", HttpStatus.OK);
	}
}
