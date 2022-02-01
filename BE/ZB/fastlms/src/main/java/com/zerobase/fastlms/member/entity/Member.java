package com.zerobase.fastlms.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import com.zerobase.fastlms.admin.model.MemberCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.Local;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode{
	@Id
	private String userId;
	 
	private String userName;
	private String phone;
	private String password;
	private LocalDateTime regDt;
	
	private boolean emailAuthYn;
	private String emailAuthKey;
	private LocalDateTime emailAuthDt;
	
	private String resetPasswordKey;
	private LocalDateTime resetPasswordDt;
	
	private boolean adminYn;
	
	private String userState;
	
	private String roles;
	
	@Column(length = 1000)
	private String refreshToken;
	
	public List<String> getRoleList(){
		if(this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<String>();
	}
	
	public void setRole(Collection<? extends GrantedAuthority> authorities) {
		//[ROLE_USER, ROLE_ADMIN]
		StringBuilder role = new StringBuilder();
		for(GrantedAuthority auth : authorities) {
			role.append(","+auth);
		}
		role.deleteCharAt(0);
		System.out.println(role.toString());
		this.roles = role.toString();
	}
}
