package com.example.mbcBoard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mbcBoard.domain.Message;
import com.example.mbcBoard.domain.User;

public interface MessageRepository extends JpaRepository<Message, Integer>{
// 		Message 엔티티의 Receiver 필드에서 user의 값과 일치하는 Message들만 List로 묶어주는 메서드
	List<Message> findAllByReceiver(User user);
	List<Message> findAllBySender(User user);
}
