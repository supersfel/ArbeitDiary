package com.zerobase.fastlms.calendar.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import com.zerobase.fastlms.calendar.dto.CalendarDto;
import com.zerobase.fastlms.calendar.entity.Calendar;
import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;
import com.zerobase.fastlms.calendar.model.responseDto.UserLists;
import com.zerobase.fastlms.calendar.repository.CalendarRepository;
import com.zerobase.fastlms.calendar.repository.DateRepository;
import com.zerobase.fastlms.calendar.service.CalendarService;
import com.zerobase.fastlms.member.service.WorkService;
import com.zerobase.fastlms.project.entity.Project;
import com.zerobase.fastlms.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalendarServiceImpl implements CalendarService{
	public static final int INIT_FORWARD_DAYS = 30; // 30일
	
	private final WorkService workService;
	
	private final CalendarRepository calendarRepository;
	private final ProjectRepository projectRepository;
	private final DateRepository dateRepository;
	
	@Override
	public boolean makeCalendar(Long id, Project project) {
		boolean result = calendarRepository.existsByProjectId(id);
		System.out.println(result);
		if(result) {
			System.out.println("이미 존재하여 생성 불가");
			return false;
		}
		Calendar calendar = Calendar.builder()
					.project(project)
					.build();
		System.out.println(LocalDateTime.now());
		System.out.println(LocalDateTime.now().plusDays(2));
		calendarRepository.save(calendar);
		/*
		 * 30일치 먼저 할당
		 */
//		LocalDateTime now = LocalDateTime.now();
//		Date date = makeDate(calendar, now, 0);
//		dateRepository.save(date);
		List<Date> dateList = new ArrayList<>();
		LocalDateTime now = LocalDateTime.now();
		for(int i=0; i < INIT_FORWARD_DAYS; i++) {
			Date date = makeDate(calendar, now, i);
			dateList.add(date);
		}
		dateRepository.saveAll(dateList);
		return true;
	}

	@Override
	public Date makeDate(Calendar calendar, LocalDateTime now, int days) {
		LocalDateTime date = now.plusDays(days);
		String[] dateTime = formatDtToStringSplitPoint(date).split("[.]");
		return Date.builder()
				.calendar(calendar)
				.year(toInteger(dateTime[0]))
				.month(toInteger(dateTime[1]))
				.day(toInteger(dateTime[2]))
				.dayOfWeek(date.getDayOfWeek().toString())
				.build();
	}
	
	@Override
	public boolean makeAllDate() {
		List<Calendar> calendars = calendarRepository.findAll();
		List<Date> dates = new ArrayList<>();
		for(Calendar calendar : calendars) {
			dates.add(makeDate(calendar, LocalDateTime.now(), 0));
		}
		dateRepository.saveAll(dates);
		return false;
	}
	
	@Override
	public String formatDtToStringSplitPoint(LocalDateTime regDt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
		return regDt != null ? regDt.format(formatter) : "";
	}
	
	public Integer toInteger(String data) {
		return Integer.parseInt(data);
	}
	
	public boolean ex(Long projectId) {
		Optional<Calendar> op = calendarRepository.findById(projectId);
		return false;
	}
	
	@Override
	public CalendarDto addWorkTime(CalendarDto calendarDto) {
		for(UserLists user : calendarDto.getUserList()) {
			List<FixedTimeInterface> userWorkTimeList = workService.getWorkTimeWProjectId(calendarDto.getProjectId(), user.getUserId());
			user.setFixeditmes(userWorkTimeList);
		}
		return calendarDto;
	}
	
}
