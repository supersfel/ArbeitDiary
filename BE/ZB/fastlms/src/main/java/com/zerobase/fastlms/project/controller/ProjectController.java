package com.zerobase.fastlms.project.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.zerobase.fastlms.memberProject.dto.UserResponseDto;
import com.zerobase.fastlms.memberProject.dto.sampleDto;
import com.zerobase.fastlms.memberProject.entity.MemberProject;
import com.zerobase.fastlms.memberProject.model.ProjectRoleType;
import com.zerobase.fastlms.project.model.ProjectInput;
import com.zerobase.fastlms.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProjectController {
	private final ProjectService projectService;
	
	@GetMapping("/newproject")
	public String newProeject() {
		System.out.println("hello");
		return "projects/newproject";
	}
	
	@PostMapping("/newproject")
	public String submitNewproject(Model model, Principal principal,ProjectInput projectInput){
		System.out.println("[post : newproejct]");
		System.out.println(projectInput+ principal.getName());
		boolean result = projectService.add(principal.getName(), projectInput);
		return "projects/newproject";
	}
	@GetMapping("/oldproject")
	public String oldProject(Model model, Principal principal) {
		System.out.println(principal);
		List<sampleDto> result = projectService.showOldProject(principal.getName());
		
		return "projects/oldproject";
	}

	@GetMapping("/project")
	public String selectProject(){
		return "projects/selectproject";
	}
	
	@GetMapping("/joinProject")
	public String joinProject() {
		return "projects/join";
	}
	
	@PostMapping("/joinProject")
	public String submitJoinProject(ProjectInput projectInput, Principal principal) {
		System.out.println("[Join]");
		System.out.println(projectInput);
		
		boolean result = projectService.join(principal.getName(), projectInput.getProjectId());
		return "projects/join";
	}
	
	@GetMapping("/outProject")
	public String outProject() {
		return "projects/out";
	}
	
	@PostMapping("/outProject")
	public String submitOutProject(ProjectInput projectInput, Principal principal) {
		System.out.println(projectInput);
		boolean result = projectService.out(principal.getName(), projectInput.getMemberProjectId());
		return "projects/out";
	}
}
