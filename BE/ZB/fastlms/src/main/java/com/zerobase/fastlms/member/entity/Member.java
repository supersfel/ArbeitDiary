package com.zerobase.fastlms.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zerobase.fastlms.admin.model.MemberCode;
//import com.zerobase.fastlms.memberProject.MemberProject;
import com.zerobase.fastlms.memberProject.entity.MemberProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "member")
public class Member implements MemberCode{
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(nullable = false)
	private String userName;
	
	private String phone;
	
	@Column(nullable = false)
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
	
	/*
	 * member가 삭제시 자식 삭제
	 */
    //@JsonManagedReference
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
	        property = "id") 
	@JsonIdentityReference(alwaysAsId = true) 
	@OneToMany(mappedBy = "member",
			fetch = FetchType.LAZY,
			//cascade = CascadeType.ALL,
			orphanRemoval =  true)
	@Builder.Default
	private List<MemberProject> projects = new ArrayList<>();
	
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
