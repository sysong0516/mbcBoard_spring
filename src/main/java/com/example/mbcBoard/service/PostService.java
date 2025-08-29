package com.example.mbcBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mbcBoard.domain.Post;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.domain.UserDTO;
import com.example.mbcBoard.repository.PostRepository;
import com.example.mbcBoard.security.SecurityUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	public void insertPost(Post post, Authentication auth) {
		UserDTO dto = securityUtil.getCurrentUser(auth);
		User user = new User();
		user.setId(dto.getId());
		post.setUser(user);
		postRepository.save(post);
	}
	
	@Transactional(readOnly = true)
	public Page<Post> getPostList(Pageable pageable){
		return postRepository.findAll(pageable);
		
	}
	
	@Transactional
	public Post getPost(int id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
		post.setCnt(post.getCnt() + 1);
		return post;
	}
	
	@Transactional
	public void updatePost(Post post) {
		Post update = getPost(post.getId());
		
		update.setTitle(post.getTitle());
		update.setContent(post.getContent());
		postRepository.save(update);
	}
	
	public void deletePost(int id) {
		postRepository.deleteById(id);
	}
	
	public Page<Post> searchPost(String type, String keyword, int page, int size) {
		// PageRequest : 데이터를 페이지 단위로 검색하고 결과를 제어하는데 사용하는 객체
		// page : 몇 번째 페이지를 요청하는 지
		// size : 한 페이지에 몇개의 데이터를 가져올지
		// Sort.by("id").descending() : 정렬 기준과 방향
		Pageable pageable = PageRequest.of(page, size,Sort.by("id").descending());
		
		switch (type) {
			case "title" :
				return postRepository.findByTitleContaining(keyword, pageable);
			case "content" :
				return postRepository.findByContentContaining(keyword, pageable);
			case "username" :
				return postRepository.findByUser_UsernameContaining(keyword, pageable);
			default:
				return Page.empty();
		}
	}
}
