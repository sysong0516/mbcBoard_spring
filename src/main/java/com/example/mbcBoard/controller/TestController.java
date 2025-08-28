package com.example.mbcBoard.controller;
import com.example.mbcBoard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.security.SecurityUtil;


@RestController
public class TestController {
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@GetMapping("/test")
	public void test(Authentication auth) {
		System.out.println(securityUtil.getCurrentUser(auth));
	}
}
