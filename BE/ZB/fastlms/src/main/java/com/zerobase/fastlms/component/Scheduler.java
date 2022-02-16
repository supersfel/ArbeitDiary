package com.zerobase.fastlms.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zerobase.fastlms.calendar.service.CalendarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Scheduler {
	private final CalendarService calendarService;
	//private final 
	/*
	 * 초 분 시 일 월 요일
	 */
	//@Scheduled(cron = "0/10 * * * * *")
	public void example() {
		System.out.println("수행중");
	}
	
	@Scheduled(cron = "59 53 13 * * *")
	public void makeAllDate() {
		System.out.println("6시 33분");
		calendarService.makeAllDate();
	}
}
