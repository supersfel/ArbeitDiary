package com.zerobase.fastlms.member.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Data
public class CustomUserDetails implements UserDetails{
	private Member member;
	private Collection<? extends GrantedAuthority> authorities;
	
	public CustomUserDetails(Member member, List<String> roleList) {
		this.member = member;
		this.authorities = this.stringToGrantedAuth(roleList);
	}
	
	public Collection<? extends GrantedAuthority> stringToGrantedAuth(List<String> roleList) {
		List<GrantedAuthority> authList = new ArrayList<>();
		for(String role : roleList) {
			authList.add(new SimpleGrantedAuthority(role));
		}
		return authList;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority>getAuthorities(){
		return authorities;
	}
	
	@Override
	public String getUsername() {
		return member.getUserId();
	}
	
	@Override
	public String getPassword() {
		return member.getPassword();
	} 

	public void setMemberRefreshToken(String token) {
		this.member.setRefreshToken(token); 
	}
	
	public String getMemberRefreshToken() {
		return this.member.getRefreshToken();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}	
}
