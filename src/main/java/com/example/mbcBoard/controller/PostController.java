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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbcBoard.domain.Post;
import com.example.mbcBoard.repository.PostRepository;
import com.example.mbcBoard.service.PostService;

@RestController
public class PostController {

    private final PostRepository postRepository;
	
	@Autowired
	private PostService postService;

    PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
	
	@PostMapping("/post/write") 
	public ResponseEntity<?> insertPost(@RequestBody Post post, Authentication auth){
		postService.insertPost(post,auth);
		
		return new ResponseEntity<>("게시글 등록 완료",HttpStatus.OK);
	}
	
	@GetMapping("/post")
	public ResponseEntity<?> getPostList(@PageableDefault(size = 20, sort = "id", direction = Direction.DESC) Pageable pagealbe ) {
		Page<Post> list = postService.getPostList(pagealbe);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/post/{id}")
	public ResponseEntity<?> getPost(@PathVariable int id, Authentication auth){
		Post post = postService.getPost(id);
		boolean isOwer = postService.authPost(auth, post);
		Map<String,Object> postUser = new HashMap<>();
		postUser.put("post",post);
		postUser.put("isOwer",isOwer);
		return new ResponseEntity<>(postUser,HttpStatus.OK);
	}
	
	@GetMapping("/postlike")
	public ResponseEntity<?> getPostLike(int id) {
		Post post = postService.getLikes(id);
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	@PostMapping("/authpost")
	public boolean authPost(@RequestBody Post post, Authentication auth) {
		
		return postService.authPost(auth, post);
	}
	
	@PutMapping("/post/modify")
	public ResponseEntity<?> updatePost(@RequestBody Post post) {
		postService.updatePost(post);
		return new ResponseEntity<>("수정 완료했습니다", HttpStatus.OK);
	}
	
	@DeleteMapping("/post")
	public ResponseEntity<?> deletePost(int id) {
		postService.deletePost(id);
		return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public Page<Post> searchPost(
			@RequestParam String type,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size	
	){
		return postService.searchPost(type, keyword, page, size);
	}
	
<<<<<<< HEAD
=======
	@GetMapping("/post/top")
	public ResponseEntity<?> bestPost(@RequestBody Post post){
		postRepository.findTop5ByOrderByLikesDesc();
		return new ResponseEntity<>("인기 게시글을 불러왔습니다.",HttpStatus.OK);
	}
>>>>>>> develop
}
