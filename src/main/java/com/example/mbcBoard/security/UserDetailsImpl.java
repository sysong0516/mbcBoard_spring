package com.example.mbcBoard.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.mbcBoard.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public UserDetailsImpl(User user) {
		this.user=user;
	}
	
	//사용자의 권한목록 반환하는 메서드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> roleList = new ArrayList<>();
		roleList.add(()->{
			return "ROLE_" + user.getRole();
		});
		
		return roleList;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	//만료된 계정인지 리턴 만료안됐으면 true
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonExpired();
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonLocked();
	}
	
	//비번 만료여부 안됐으면 true
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isCredentialsNonExpired();
	}
	
	//활성화되어있는지 활성화상태면 true
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return UserDetails.super.isEnabled();
	}
	
}
