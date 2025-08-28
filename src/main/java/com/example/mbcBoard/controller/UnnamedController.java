package com.example.mbcBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.domain.UnnamedPost;
import com.example.mbcBoard.service.UnnamedPostService;

@RestController
public class UnnamedController {

	@Autowired
	public UnnamedPostService unnamedPostService;
	
	@GetMapping("/unnamed")
	public ResponseEntity<?> getList(@PageableDefault(size = 20, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<UnnamedPost> list = unnamedPostService.getBoardList(pageable);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/unnamed/write")
	public ResponseEntity<?> insertBoard(@RequestBody UnnamedPost unnamedPost) {
		unnamedPostService.insert(unnamedPost);
		return new ResponseEntity<>("게시글 등록 완료",HttpStatus.OK);
	}
	
	@GetMapping("/unnamed/{id}")
	public ResponseEntity<?> getBoard(@PathVariable int id) {
		UnnamedPost unnamedPost = unnamedPostService.getBoard(id);
		return new  ResponseEntity<>(unnamedPost, HttpStatus.OK);
	}
	
	
}
