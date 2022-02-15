package com.zerobase.fastlms.calendar.controller;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.fastlms.calendar.dto.CalendarDto;
import com.zerobase.fastlms.calendar.entity.Calendar;
import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.model.CalendarInput;
import com.zerobase.fastlms.calendar.model.CalendarInterface;
import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.calendar.model.DatesDto;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;
import com.zerobase.fastlms.calendar.model.UserListsDto;
import com.zerobase.fastlms.calendar.model.WorkUserListRequest;
import com.zerobase.fastlms.calendar.model.enuum.DayEnum;
import com.zerobase.fastlms.calendar.repository.CalendarRepository;
import com.zerobase.fastlms.calendar.repository.DateRepository;
import com.zerobase.fastlms.calendar.service.CalendarService;
import com.zerobase.fastlms.datemember.service.DateMemberService;
import com.zerobase.fastlms.member.entity.Work;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.SelectAutoSelf;
import com.zerobase.fastlms.member.repository.WorkRepository;
import com.zerobase.fastlms.member.service.WorkService;
import com.zerobase.fastlms.project.entity.Project;
import com.zerobase.fastlms.project.model.ProjectInput;
import com.zerobase.fastlms.project.model.ProjectListInterface;
import com.zerobase.fastlms.project.repository.ProjectRepository;
import com.zerobase.fastlms.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiCalendarController {
	private final WorkService workService;
	private final CalendarService calendarService;
	private final DateMemberService dateMemberService;
	private final ProjectService projectService;
	
	private final ProjectRepository projectRepository;
	private final CalendarRepository calendarRepository;
	private final WorkRepository workRepository;
	
	private final DateRepository dateRepository;
	
	@PostMapping("/api/code")
	public ResponseEntity<?> submitCalendar(@RequestBody ProjectInput projectInput) {
		System.out.println(projectInput);
		//Optional<Calendar> oc = calendarRepository.findByProjectId(projectInput.getProjectId());
		Optional<Project> op = projectRepository.findById(projectInput.getProjectId());
//		Long calendarId = oc.get().getCalendarId();
//		List<Date> dates = oc.get().getDates();
		CalendarInterface calendarResponse = calendarRepository.findByProjectId(projectInput.getProjectId());
		
		return ResponseEntity.ok().body(calendarResponse);
	}
	
	@PostMapping("api/load")
	public ResponseEntity<?> load(@RequestBody CalendarInput calendarInput, Principal principal){
		System.out.println("[LOAD]");
		CalendarUserList calendarUserList = projectService.getUserList(calendarInput.getProjectId());
		CalendarInterface calendarResponse = calendarRepository.findByProjectId(calendarInput.getProjectId());
		CalendarDto calendarDto = new CalendarDto(calendarUserList, calendarResponse);
		CalendarDto resultCalendar = calendarService.addWorkTime(calendarDto);
		System.out.println("=============================================");
		System.out.println(calendarDto.equals(resultCalendar));
		System.out.println("=============================================");
		return ResponseEntity.ok().body(resultCalendar);
	}
	
	@PostMapping("api/auto")
	public ResponseEntity<?> self(@RequestBody WorkUserListRequest workUserList, Principal principal){
		System.out.println(workUserList);
		List<Date> beforeUserList = dateMemberService.exceptAutoTime(workUserList.getProjectId(), workUserList.getCalendarId());
		System.out.println("[이전 값 찾기 완료]==========================================================");
		boolean isUserListUpdate = workService.updateUserList(workUserList, principal.getName());
		System.out.println("[현재 유저 데이터 업데이트 완료] : =========================================================="+isUserListUpdate);
		CalendarUserList calendarUserList = projectService.getUserList(workUserList.getProjectId());
		CalendarDto calendarDto = new CalendarDto(calendarUserList); 
		CalendarDto resultCalendar = calendarService.addWorkTime(calendarDto);
		System.out.println("[userList값 가져오기 성공]==========================================================");
		boolean result = dateMemberService.updateDailyWork(workUserList.getCalendarId(), resultCalendar.getUserList(), beforeUserList);
		System.out.println("??");
		return ResponseEntity.ok().body(beforeUserList);
	}
	
	@PostMapping("api/update")
	public ResponseEntity<?> calendar(@RequestBody WorkUserListRequest workUserList, Principal principal){
		System.out.println("=============================================");
		boolean result = dateMemberService.loadDailyWork(workUserList.getCalendarId(), workUserList.getDates());
		System.out.println("=============================================");
		CalendarUserList calendarUserList = projectService.getUserList(workUserList.getProjectId());
		CalendarInterface calendarResponse = calendarRepository.findByProjectId(workUserList.getProjectId());
		CalendarDto calendarDto = new CalendarDto(calendarUserList, calendarResponse);
		//CalendarDto resultCalendar = calendarService.addWorkTime(calendarDto);
		return ResponseEntity.ok().body(calendarDto);
	}
}
