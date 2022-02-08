package com.zerobase.fastlms.memberProject.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserListDto {
	Long id;
	String userId;
	Long projectId;
	String userName;
	String phone;
	ProjectRoleType projectRole;
	LocalDateTime regDt;
	
	public UserListDto(Long id, String userId,
			Long projectId, String userName, String phone,
			ProjectRoleType projectRole, LocalDateTime regDt) {
		this.id = id;
		this.userId = userId;
		this.projectId = projectId;
		this.userName = userName;
		this.phone = phone;
		this.projectRole = projectRole;
		this.regDt = regDt;
	}
}
