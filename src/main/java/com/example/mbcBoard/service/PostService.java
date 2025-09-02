package com.example.mbcBoard.service;

import java.util.HashMap;
import java.util.Map;

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
import com.example.mbcBoard.repository.UserRepository;
import com.example.mbcBoard.security.SecurityUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
	
	@Transactional(readOnly = true)
	public Post findPost(int id) {
		return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
	}
	
	@Transactional
	public Post getPost(int id) {
		Post post = findPost(id);
		post.setCnt(post.getCnt() + 1);
		return post;
	}
	
	public boolean authPost(Authentication auth, Post post) {
		UserDTO dto = securityUtil.getCurrentUser(auth);
		return post.getUser().getId() == dto.getId();
	}
	
	@Transactional
	public void updatePost(Post post) {
		Post update = findPost(post.getId());

		update.setTitle(post.getTitle());
		update.setContent(post.getContent());
		postRepository.save(update);

	}
	
	@Transactional
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
	
	@Transactional
	public Map<String, Object> toggleLike(int postId, Authentication auth) {
		UserDTO meDto = securityUtil.getCurrentUser(auth);
		User me = userRepository.findById(meDto.getId()).get();
		
		
		postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
		
		 boolean liked;
	        if (postRepository.existsByIdAndLikers_Id(postId, me.getId())) {
	            // 이미 눌렀으면 취소
	            postRepository.deleteLikerLink(postId, me.getId()); // 조인 테이블에서 바로 삭제
	            liked = false;
	        } else {
	            // 아직 안 눌렀으면 추가 (컬렉션에 add → 조인 테이블 insert)
	            Post ref = postRepository.getReferenceById(postId); // 엔티티 전체 로딩 없이 프록시 참조
	            ref.getLikers().add(me);
	            liked = true;
	        }

	        long count = postRepository.countLikers(postId);
	        Map<String, Object> res = new HashMap<>();
	        res.put("liked", liked);
	        res.put("likeCount", count);
	        return res;
	}
	
	@Transactional(readOnly = true)
    public long likeCount(int postId) {
        return postRepository.countLikers(postId);
    }
	
}
