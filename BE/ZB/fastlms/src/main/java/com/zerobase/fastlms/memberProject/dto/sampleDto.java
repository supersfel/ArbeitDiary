package com.zerobase.fastlms.memberProject.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.memberProject.entity.MemberProject;
import com.zerobase.fastlms.memberProject.model.ProjectRoleType;
import com.zerobase.fastlms.memberProject.model.UserListInterface;
import com.zerobase.fastlms.project.dto.ProjectDto;
import com.zerobase.fastlms.project.entity.Project;
import com.zerobase.fastlms.project.model.ProjectListInterface;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class sampleDto {

	Long id;	
	ProjectRoleType projectRole;
	LocalDateTime regDt;
	
	MemberDto member;
	ProjectDto project;
	
	public static List<sampleDto> of(List<MemberProject> lists){
		if(lists != null) {
			List<sampleDto> memberProjectDto = new ArrayList<>();
			for(MemberProject list : lists) {
				memberProjectDto.add(of(list));
			}
			return memberProjectDto;
		}
		return null;
	}
	public static sampleDto of(MemberProject list) {
		return sampleDto.builder()
				.id(list.getId())
				.projectRole(list.getProjectRole())
				.regDt(list.getRegDt())
				.member(MemberDto.of(list.getMember()))
				.project(ProjectDto.of(list.getProject()))
				.build();
	}
}
