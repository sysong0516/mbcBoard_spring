package com.example.mbcBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mbcBoard.domain.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer>{

}
