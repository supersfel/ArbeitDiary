package com.zerobase.fastlms.project.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.zerobase.fastlms.project.entity.Project;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectDto {
	Long projectId;
	String projectName;
	LocalDateTime regDt;
	
	long totalCount;
	long projectNum;
	
	public static List<ProjectDto> of(List<Project> lists){
		if(lists != null) {
			List<ProjectDto> projectDto = new ArrayList<ProjectDto>();
			
			for(Project list : lists) {
				projectDto.add(of(list));
			}
			return projectDto;
		}
		return null;
	}
	
	public static ProjectDto of(Project project) {
		return ProjectDto.builder()
				.projectId(project.getId())
				.projectName(project.getProjectName())
				.regDt(project.getRegDt())
				.build();
	}
}
