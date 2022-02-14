package com.zerobase.fastlms.memberProject;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.fastlms.project.model.ProjectInput;
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
	
	@PostMapping("/api/newproject")
	public ResponseEntity<?> submitNewproject(Model model, Principal principal,@RequestBody ProjectInput projectInput){
		System.out.println("[post : newproejct]");
		System.out.println(projectInput+ principal.getName());
		boolean result = projectService.add(principal.getName(), projectInput);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/deleteproject")
	public ResponseEntity<?> submitDeleteproject(Model model, Principal principal,@RequestBody ProjectInput projectInput){
		System.out.println("[post : deleteProejct]");
		System.out.println(projectInput+ principal.getName());
		boolean result = projectService.out(principal.getName(), projectInput);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/joinproject")
	public ResponseEntity<?> submitJoinproject(Model model, Principal principal,@RequestBody ProjectInput projectInput){
		System.out.println("[post : newproejct]");
		System.out.println(projectInput+ principal.getName());
		boolean result = projectService.join(principal.getName(), projectInput.getProjectId());
		return ResponseEntity.ok().body(result);
	}
}
