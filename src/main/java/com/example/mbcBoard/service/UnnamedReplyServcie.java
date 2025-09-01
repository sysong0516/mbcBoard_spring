package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mbcBoard.domain.UnnamedReply;
import com.example.mbcBoard.repository.UnnamedPostRepository;
import com.example.mbcBoard.repository.UnnamedReplyRepository;
import com.example.mbcBoard.repository.UserRepository;

@Service
public class UnnamedReplyServcie {
	
	@Autowired
	private UnnamedReplyRepository unnamedReplyRepository;
	
	@Autowired
	private UnnamedPostRepository unnamedpostRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void insertReply(UnnamedReply reply, int postId, int userId) {
		
		reply.setPost(unnamedpostRepository.findById(postId).get());
		reply.setUser(userRepository.findById(userId).get());
		
		unnamedReplyRepository.save(reply);
	}
	
	@Transactional
	public void deleteReply(int id) {
		unnamedReplyRepository.deleteById(id);
	}
	
	public boolean authUser(int id, int request) {
		UnnamedReply reply = unnamedReplyRepository.findById(id).get();
		return reply.getUser().getId()==request;
	}
	
}
