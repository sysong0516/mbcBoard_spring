package com.example.mbcBoard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
	
	@GetMapping("/unnamedlike")
	public ResponseEntity<?> getPostLike(int postId) {
		long count = unnamedPostService.likeCount(postId);
		return new ResponseEntity<>(count,HttpStatus.OK);
	}
	
	@PostMapping("/unnamed/{id}/like") // 한 버튼으로 토글
	public ResponseEntity<?> toggleLike(@PathVariable int id, Authentication auth) {
	    Map<String, Object> body = unnamedPostService.toggleLike(id, auth);
	    return new ResponseEntity<>(body, HttpStatus.OK);
	}
	@GetMapping("/unnamedBest")
	public ResponseEntity<?> best(){
		Map<String, List<UnnamedPost>> best = new HashMap<>();
		best.put("content", unnamedPostService.getBestPost());
		
		return new ResponseEntity<>(best,HttpStatus.OK);
	}
	
}
