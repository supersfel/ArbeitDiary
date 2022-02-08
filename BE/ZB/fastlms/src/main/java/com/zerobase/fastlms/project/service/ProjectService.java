package com.zerobase.fastlms.project.service;

import java.security.Principal;
import java.util.List;

import com.zerobase.fastlms.member.model.MemberResponseDto;
import com.zerobase.fastlms.memberProject.dto.UserResponseDto;
import com.zerobase.fastlms.memberProject.dto.sampleDto;
import com.zerobase.fastlms.memberProject.entity.MemberProject;
import com.zerobase.fastlms.memberProject.model.UserListDto;
import com.zerobase.fastlms.memberProject.model.UserListInterface;
import com.zerobase.fastlms.project.dto.ProjectDto;
import com.zerobase.fastlms.project.model.ProjectInput;
import com.zerobase.fastlms.project.model.ProjectListInterface;
import com.zerobase.fastlms.project.model.ProjectParam;

public interface ProjectService {
	boolean add (String userId,ProjectInput projectInput);
	List<ProjectDto> list(ProjectParam projectParam);
	
	List<sampleDto> showOldProject(String userId);
	boolean join(String userId, Long projectId);
	boolean out(String userId, Long projectId);
	String responseOldProject(String userId);
}
