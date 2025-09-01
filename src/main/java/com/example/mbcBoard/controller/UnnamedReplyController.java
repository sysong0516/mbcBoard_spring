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

import com.example.mbcBoard.domain.UnnamedReply;
import com.example.mbcBoard.security.SecurityUtil;
import com.example.mbcBoard.service.UnnamedReplyServcie;

@Controller
public class UnnamedReplyController {
	
	@Autowired
	private UnnamedReplyServcie unnamedReplyService;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@PostMapping("/unnamedReply/{postId}")
	public ResponseEntity<?> insertReply(@PathVariable int postId, @RequestBody UnnamedReply reply, Authentication auth){
		
		int userId=securityUtil.getCurrentUser(auth).getId();
		unnamedReplyService.insertReply(reply, postId, userId);
		
		return new ResponseEntity<>("등록완료", HttpStatus.OK);
	}
	@PutMapping("/unnamedReply/{postId}")
	public ResponseEntity<?> updateReply(@RequestBody UnnamedReply reply, @PathVariable int postId, Authentication auth){
		
		int userId=securityUtil.getCurrentUser(auth).getId();
		
		if(!unnamedReplyService.authUser(reply.getId(), userId)) {
			return new ResponseEntity<>("작성자만 수정할수있습니다", HttpStatus.BAD_REQUEST);
		}
		unnamedReplyService.insertReply(reply, postId, userId);
		
		return new ResponseEntity<>("수정완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/unnamedReply/{id}")
	public ResponseEntity<?> deleteReply(@PathVariable Integer id, Authentication auth){
		
		int userId=securityUtil.getCurrentUser(auth).getId();
		if(!unnamedReplyService.authUser(id, userId)) {
			return new ResponseEntity<>("작성자만 삭제할수있습니다", HttpStatus.BAD_REQUEST);
		}
		unnamedReplyService.deleteReply(id);
		
		return new ResponseEntity<>("삭제완료", HttpStatus.OK);
	}
}
