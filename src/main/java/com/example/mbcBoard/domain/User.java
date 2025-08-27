package com.example.mbcBoard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 회원번호
	
	@Column(nullable = false, length = 50, unique = true)
	private String username; // 아이디
	
	@Column(length = 100)
	private String password; // 비밀번호
	
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@Column(nullable = false, length = 100)
	private String email; // 이메일
}
