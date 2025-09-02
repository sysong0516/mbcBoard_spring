package com.example.mbcBoard.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	private int cnt;
	
	
	 @ManyToMany(fetch = FetchType.LAZY)
	 @JoinTable(
	       name = "post_liker",
	       joinColumns = @JoinColumn(name = "post_id"),
	       inverseJoinColumns = @JoinColumn(name = "user_id"),
	        // 제약 조건
	        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "user_id"})
	 )
	// 응답에 통째로 내보내지 않으려면 아래 주석 해제
	// @JsonIgnore
	private Set<User> likers = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")	
	private User user;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
	@OrderBy("id asc")
	@JsonManagedReference
	private List<Reply> replyList;
}
