package com.example.mbcBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mbcBoard.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
