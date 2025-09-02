package com.example.mbcBoard.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mbcBoard.domain.Post;
import com.example.mbcBoard.domain.UnnamedPost;
import com.example.mbcBoard.domain.User;
import com.example.mbcBoard.domain.UserDTO;
import com.example.mbcBoard.repository.UnnamedPostRepository;
import com.example.mbcBoard.repository.UserRepository;
import com.example.mbcBoard.security.SecurityUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UnnamedPostService {
	
	@Autowired
	private UnnamedPostRepository unnamedPostRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	public void insert(UnnamedPost unnamed) {
		unnamedPostRepository.save(unnamed);
	}
	
	
	@Transactional(readOnly = true)
	public Page<UnnamedPost> getBoardList(Pageable pageable){
		return unnamedPostRepository.findAll(pageable);
	}
	
	@Transactional
	public UnnamedPost getBoard(int id) {
		UnnamedPost board = unnamedPostRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없십니다."));
		board.setCnt(board.getCnt()+1);
		return board;
	}
	
	@Transactional
	public Map<String, Object> toggleLike(int postId, Authentication auth) {
		UserDTO meDto = securityUtil.getCurrentUser(auth);
		User me = userRepository.findById(meDto.getId()).get();
		
		
		unnamedPostRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
		
		 boolean liked;
	        if (unnamedPostRepository.existsByIdAndLikers_Id(postId, me.getId())) {
	            // 이미 눌렀으면 취소
	        	unnamedPostRepository.deleteLikerLink(postId, me.getId()); // 조인 테이블에서 바로 삭제
	            liked = false;
	        } else {
	            // 아직 안 눌렀으면 추가 (컬렉션에 add → 조인 테이블 insert)
	            UnnamedPost ref = unnamedPostRepository.getReferenceById(postId); // 엔티티 전체 로딩 없이 프록시 참조
	            ref.getLikers().add(me);
	            liked = true;
	        }

	        long count = unnamedPostRepository.countLikers(postId);
	        Map<String, Object> res = new HashMap<>();
	        res.put("liked", liked);
	        res.put("likeCount", count);
	        return res;
	}
	
	@Transactional(readOnly = true)
    public long likeCount(int postId) {
        return unnamedPostRepository.countLikers(postId);
    }
	
}
