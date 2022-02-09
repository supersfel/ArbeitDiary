package com.zerobase.fastlms.project.model;

import lombok.Data;

@Data
public class ProjectInput {
	private String projectName;
	/*
	 * 프로젝트 분류 코드
	 */
	private Long projectId;
	
	/*
	 * 참여 번호
	 */
	private Long joinId;
}
