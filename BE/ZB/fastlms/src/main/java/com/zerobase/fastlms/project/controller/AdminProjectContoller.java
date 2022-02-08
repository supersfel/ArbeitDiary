package com.zerobase.fastlms.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.zerobase.fastlms.admin.controller.BaseController;
import com.zerobase.fastlms.project.dto.ProjectDto;
import com.zerobase.fastlms.project.model.ProjectParam;
import com.zerobase.fastlms.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminProjectContoller extends BaseController{

	private final ProjectService projectService;
	
	@GetMapping("/admin/project/list.do")
	public String list(Model model, ProjectParam projectParam) {
		System.out.println("[projectList]");
		projectParam.init();
		System.out.println("전");
		List<ProjectDto> projects = projectService.list(projectParam);
		System.out.println(projects);
		long totalCount = 0;
		
		if(!CollectionUtils.isEmpty(projects)) {
			totalCount = projects.get(0).getTotalCount();
		}
		String queryString = projectParam.getQueryString();
		System.out.println("[projectDto] : "+projects);
		String pagerHtml = getPagerHtml(totalCount, projectParam.getPageSize(), projectParam.getPageIndex(), queryString);
		System.out.println("[projectPager] : "+pagerHtml);
		model.addAttribute("lists", projects);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);
		return "admin/project/list";
	}
}
