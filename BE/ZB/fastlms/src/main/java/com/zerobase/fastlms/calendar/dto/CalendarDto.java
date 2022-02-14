package com.zerobase.fastlms.calendar.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerobase.fastlms.calendar.model.CalendarInterface;
import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;
import com.zerobase.fastlms.calendar.model.responseDto.UserLists;
import com.zerobase.fastlms.member.model.SelectAutoSelf;
import com.zerobase.fastlms.member.repository.WorkRepository;
import com.zerobase.fastlms.member.service.WorkService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CalendarDto {
	String calendarId;
	String projectId;
	String projectName;
	List<UserLists> userList = new ArrayList<>();
	List<CalendarInterface.CustomDate> dates = new ArrayList<>();
	
	public CalendarDto() {}
	public CalendarDto(CalendarUserList calendarUserList,CalendarInterface calendarInterface) {
		this.calendarId = calendarInterface.getCalendarId();
		this.projectId = calendarUserList.getProjectId();
		this.projectName = calendarUserList.getProjectName();
		
		setUserList(calendarUserList.getUserList());
		setDates(calendarInterface.getDates());
	}
	
	public void setUserList(List<CalendarUserList.userList> userList){
		for(CalendarUserList.userList user: userList) {
			this.userList.add(new UserLists(user));
		}
	}
	public void setDates(List<CalendarInterface.CustomDate> dates) {
		this.dates = dates;
	}
}



