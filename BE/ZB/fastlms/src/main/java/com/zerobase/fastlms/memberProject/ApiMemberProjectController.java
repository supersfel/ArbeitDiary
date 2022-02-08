package com.zerobase.fastlms.memberProject;

import java.security.Principal;
import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.fastlms.member.model.MemberResponseDto;
import com.zerobase.fastlms.memberProject.dto.UserResponseDto;
import com.zerobase.fastlms.memberProject.entity.MemberProject;
import com.zerobase.fastlms.memberProject.model.UserListDto;
import com.zerobase.fastlms.memberProject.model.UserListInterface;
import com.zerobase.fastlms.project.model.ProjectListInterface;
import com.zerobase.fastlms.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiMemberProjectController {
	private final ProjectService projectService;
	
	@PostMapping("/api/oldproject")
	public ResponseEntity<?> list(Principal principal){
		System.out.println("?:"+principal.getName());
		String result = projectService.responseOldProject(principal.getName());
		System.out.println("result : "+result);
		return ResponseEntity.ok().body(result);
	}
}
