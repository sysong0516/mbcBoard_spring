package com.example.mbcBoard.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.domain.RoleType;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.jwt.JwtService;
import com.example.mbcBoard.repository.UserRepository;
import com.example.mbcBoard.security.SecurityUtil;
import com.example.mbcBoard.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityUtil securityUtil;	
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;

    
	
	@PostMapping("/signup")
	public ResponseEntity<?> insertUser(@Valid @RequestBody User member, BindingResult bindingResult){
		
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		member.setRole(RoleType.USER);
		
		userService.insertUser(member);
		
		return new ResponseEntity<>("가입성공",HttpStatus.OK);
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
	
	@GetMapping("/userInfo")
	public ResponseEntity<?> info(Authentication auth){
		return new ResponseEntity<>(securityUtil.getCurrentUser(auth).getId(),HttpStatus.OK);
	}
	 @GetMapping("/userList")
	    public List<User> getUserList() {
	        return userRepository.findAll();
	    }
}
