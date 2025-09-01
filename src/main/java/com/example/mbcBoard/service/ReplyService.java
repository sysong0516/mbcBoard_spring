package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mbcBoard.domain.Reply;
import com.example.mbcBoard.repository.PostRepository;
import com.example.mbcBoard.repository.ReplyRepository;
import com.example.mbcBoard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void insertReply(Reply reply, int postId, int userId) {
		
		reply.setPost(postRepository.findById(postId).get());
		reply.setUser(userRepository.findById(userId).get());
		
		replyRepository.save(reply);
	}
	
	@Transactional
	public void deleteReply(int id) {
		replyRepository.deleteById(id);
	}
	
	public boolean authUser(int id, int request) {
		Reply reply = replyRepository.findById(id).get();
		return reply.getUser().getId()==request;
	}
	
}
