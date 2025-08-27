package com.example.mbcBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.domain.ResponseDTO;
import com.example.mbcBoard.domain.RoleType;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.jwt.JwtService;
import com.example.mbcBoard.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/signup")
	public ResponseDTO<?> insertUser(@RequestBody User member){
		
		member.setRole(RoleType.USER);
		
		userService.insertUser(member);
		
		return new ResponseDTO<>(HttpStatus.OK.value(), "가입성공");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User member) {
		UsernamePasswordAuthenticationToken cred = 
				new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
		
		Authentication auth = authenticationManager.authenticate(cred);
		
		String jwt = jwtService.createToken(auth.getName(), auth.getAuthorities());
		
		return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
					.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
					.build();
	}
}
