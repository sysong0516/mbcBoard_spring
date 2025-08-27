package com.example.mbcBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.mbcBoard.service.UnnamedReplyServcie;

@Controller
public class UnnamedReplyController {
	
	@Autowired
	private UnnamedReplyServcie unnamedReplyService;
	
}
