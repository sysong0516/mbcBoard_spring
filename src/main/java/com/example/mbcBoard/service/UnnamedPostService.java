package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mbcBoard.repository.UnnamedPostRepository;

@Service
public class UnnamedPostService {
	
	@Autowired
	private UnnamedPostRepository unnamePostRepository;
	
}
