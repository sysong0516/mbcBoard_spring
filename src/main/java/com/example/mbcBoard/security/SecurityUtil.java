package com.example.mbcBoard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.mbcBoard.domain.UserDTO;
import com.example.mbcBoard.repository.UserRepository;

@Component
public class SecurityUtil {
    
    @Autowired
    private UserRepository userRepository;
    public UserDTO getCurrentUser(Authentication auth) {
    	UserDTO userDTO=new UserDTO(userRepository.findByUsername(auth.getName()).get());
    	return userDTO;
    }
}