package com.example.mbcBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.service.UnnamedPostService;

@RestController
public class UnnamedController {

	@Autowired
	public UnnamedPostService unnamedPostService;
	
	
}
