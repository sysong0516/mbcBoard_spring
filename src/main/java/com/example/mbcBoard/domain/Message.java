package com.example.mbcBoard.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 메시지번호
	
	@Column(nullable = false)
	private String content; // 메세지 내용
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@Column(nullable = false)
	private boolean deletedBySender; // 보낸 메세지 삭제
	
	@Column(nullable = false)
	private boolean deletedByReceiver; // 받은 사람의 탈퇴여부를 파악
	
	@ManyToOne(fetch = FetchType.LAZY) // message(N):id(1) + 지연로딩
	@JoinColumn(name = "sender_id") // sender_id(엔티티)랑 연관 (이해 필요)
	@OnDelete(action = OnDeleteAction.NO_ACTION) //연관 된 엔티티가 삭제 될 때 어떤 동작을 할지 정의
	// 												(작성자 혹은 수신자가 계정을 삭제하면 같이 지우기 위함)
	private User sender; // 보낸 유저
	
	@ManyToOne(fetch = FetchType.LAZY) // message(N):id(1) + 지연로딩
	@JoinColumn(name = "receiver_id") // recevier_id(엔티티)랑 연관
	@OnDelete(action = OnDeleteAction.NO_ACTION) //연관 된 엔티티가 삭제 될 때 어떤 동작을 할지 정의
	private User receiver; // 받는 메세지 (이해 필요 - User라는 엔티티를 참조하는 엔티티?)
	
	public void deleteBySender() {
		// 보낸 메세지를 삭제하면 true로 반환
		this.deletedBySender = true;
	}
	
	public void deleteByReceiver() {
		this.deletedByReceiver = true;
	}
	
	public boolean isDeleted() {
		// deleteBySender과 deleteByReceiver이 둘 다 true면 true를 반환
		// 즉 보낸 메세지와 받은 메세지가 둘 다 true일 때 DB에서 삭제하기 위함
		return isDeletedBySender() && isDeletedByReceiver();
	}
	
}
