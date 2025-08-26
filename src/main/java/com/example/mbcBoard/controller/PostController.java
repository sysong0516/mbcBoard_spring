package com.example.mbcBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.mbcBoard.service.PostService;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
}
