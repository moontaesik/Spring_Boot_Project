package com.reservation.foodTable.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.enumClass.Role;

@SuppressWarnings("serial")
public class FoodMemberDetails implements UserDetails{
	
	private Member member;
	
	public FoodMemberDetails(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Role roles = member.getRole();
		
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		

		authories.add(new SimpleGrantedAuthority(roles.toString()));

		
		return authories;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getFullname() {
		return member.getName();
	}
}
