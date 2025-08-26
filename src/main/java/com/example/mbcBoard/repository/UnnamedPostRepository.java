package com.example.mbcBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mbcBoard.domain.UnnamedPost;

@Repository
public interface UnnamedPostRepository extends JpaRepository<UnnamedPost, Integer>{

}
