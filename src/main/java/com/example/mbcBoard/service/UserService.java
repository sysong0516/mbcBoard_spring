package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void insertUser(User user) {
		
		userRepository.save(user);
		
	}
	
}
