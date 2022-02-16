package com.zerobase.fastlms.memberProject.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerobase.fastlms.memberProject.entity.MemberProject;
import com.zerobase.fastlms.project.entity.Project;

public interface UserListInterface {
	String getUserId();
	String getUserName();
	String getPhone();
	List<projects> getProjects();
	//List<MemberProject> getProjects();
	
	interface projects{
		Long getId();
		String getProjectRole();
		LocalDateTime getRegDt();
		
		@JsonIgnore
		Project getProject();
	}
}
