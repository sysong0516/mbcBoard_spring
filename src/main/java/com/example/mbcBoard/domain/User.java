package com.example.mbcBoard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 회원번호
	
	@Column(nullable = false, length = 50, unique = true)
	@NotBlank(message = "닉네임을 입력해주세요")
	@Size(min = 3, max = 10, message= "닉네임은 3~10글자여야 합니다")
	private String username; // 아이디
	
	@Column(nullable = false, length = 100)
	@Size(min = 4, max = 100, message= "비밀번호는 4~100글자여야 합니다")
	@NotBlank(message = "비밀번호를 입력해주세요")
	private String password; // 비밀번호
	
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@Column(nullable = false, length = 100)
	@NotBlank(message = "email을 입력해주세요")
	@Email(message = "email 형식이 아닙니다")
	private String email; // 이메일
}
