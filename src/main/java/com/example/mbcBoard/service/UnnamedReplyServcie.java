package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mbcBoard.repository.UnnamedReplyRepository;

@Service
public class UnnamedReplyServcie {
	
	@Autowired
	private UnnamedReplyRepository unnamedReplyRepository;
	
}
