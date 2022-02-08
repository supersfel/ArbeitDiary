package com.zerobase.fastlms.member.model;

import java.util.List;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.project.dto.ProjectDto;
import com.zerobase.fastlms.project.entity.Project;

import lombok.Data;

@Data
public class MemberResponseDto {
	MemberDto member;
	
	List<ProjectDto> projects;
	
	public static MemberResponseDto of() {
		return null;
	}
}
/*
 * {
    id: "1",
    userName: "정민규",
    projects: [
      {
        projectId: "1",
        projectName: "서브웨이 연수점",
        calendarId: "firstcalendar",
        userList: [
          { userName: "정민규", userId: "supersfel@naver.com", done: false },
          {
            userName: "박세연",
            userId: "parkseyeon99@naver.com",
            done: true,
          },
        ],
      },
      {
        projectId: "2",
        projectName: "맘스터치 개봉점",
        calendarId: "secondecalendar",
        userList: [
          { userName: "짭민규", userId: "supersfel@naver.com", done: true },
          {
            userName: "짭세연",
            userId: "parkseyeon99@naver.com",
            done: false,
          },
        ],
      },
    ],
  } 
 */
