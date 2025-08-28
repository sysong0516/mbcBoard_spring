package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mbcBoard.domain.UnnamedPost;
import com.example.mbcBoard.repository.UnnamedPostRepository;

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
	
	public UnnamedPost getBoard(int id) {
		return unnamedPostRepository.findById(id).get();
	}
	
}
