package com.example.mbcBoard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.repository.UserRepository;

@Component
public class SecurityUtil {
    
    @Autowired
    private UserRepository userRepository;
    public User getCurrentUser(Authentication auth) {
    	return userRepository.findByUsername(auth.getName()).get();
    	
    }
}