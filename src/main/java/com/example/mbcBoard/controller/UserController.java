package com.example.mbcBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mbcBoard.domain.ResponseDTO;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	@ResponseBody
	public ResponseDTO<?> insertUser(@RequestBody User member){
		
		userService.insertUser(member);
		
		return new ResponseDTO<>(HttpStatus.OK.value(), "가입성공");
	}
}
