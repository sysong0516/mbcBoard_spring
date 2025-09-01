package com.example.mbcBoard.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mbcBoard.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	// select ... where id like '%?%'
	Page<Post> findByTitleContaining(String keyword, Pageable pageable);
	Page<Post> findByContentContaining(String keyword, Pageable pageable);
	Page<Post> findByUser_UsernameContaining(String keyword, Pageable pageable);
	
}
