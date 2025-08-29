package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mbcBoard.domain.Post;
import com.example.mbcBoard.domain.UnnamedPost;
import com.example.mbcBoard.repository.UnnamedPostRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UnnamedPostService {
	
	@Autowired
	private UnnamedPostRepository unnamedPostRepository;
	
	public void insert(UnnamedPost unnamed) {
		unnamedPostRepository.save(unnamed);
	}
	
	
	@Transactional(readOnly = true)
	public Page<UnnamedPost> getBoardList(Pageable pageable){
		return unnamedPostRepository.findAll(pageable);
	}
	
	@Transactional
	public UnnamedPost getBoard(int id) {
		UnnamedPost board = unnamedPostRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없십니다."));
		board.setCnt(board.getCnt()+1);
		return board;
	}
	
	public UnnamedPost getLikes(int id) {
		UnnamedPost unlike = unnamedPostRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
		unlike.setLikes(unlike.getLikes()+1);
		unnamedPostRepository.save(unlike);
		return unlike;
	}
	
}
