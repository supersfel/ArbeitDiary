package com.zerobase.fastlms.project.service;

import java.util.List;

import com.zerobase.fastlms.calendar.model.CalendarInput;
import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.memberProject.dto.sampleDto;
import com.zerobase.fastlms.project.dto.ProjectDto;
import com.zerobase.fastlms.project.model.ProjectInput;
import com.zerobase.fastlms.project.model.ProjectParam;

public interface ProjectService {
	boolean add (String userId,ProjectInput projectInput);
	List<ProjectDto> list(ProjectParam projectParam);
	
	List<sampleDto> showOldProject(String userId);
	boolean join(String userId, Long projectId);
	boolean out(String userId, ProjectInput projectInput);
	String responseOldProject(String userId);
	
	CalendarUserList getUserList(Long projectId);
}
