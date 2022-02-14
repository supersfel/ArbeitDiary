package com.zerobase.fastlms.calendar.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.zerobase.fastlms.calendar.service.CalendarService;
import com.zerobase.fastlms.project.entity.Project;
import com.zerobase.fastlms.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CalendarController {
	private final CalendarService calendarService;
	private final ProjectRepository projectRepository;
	@GetMapping("/makeC")
	public String makeC() {
		System.out.println("??");
		//calendarService.makeCalendar(4L);
		return "calendar/makeC";
	}
	
	@PostMapping("/makeC")
	public String submitMakeC(Long calendar) {
		System.out.println("CODE : " + calendar);
		Project project = projectRepository.findById(calendar).get();
		boolean result = calendarService.makeCalendar(calendar, project);
		System.out.println("WHAT?");
		return "calendar/makeC";
	}
	
	@GetMapping("/code")
	public String calendar() {
		return "calendar/code";
	}
	
	@PostMapping("/code")
	public String submitCalendar(Long projectId) {
		System.out.println(projectId);
		boolean result = calendarService.ex(projectId);
		return "calendar/code";
	}
}
