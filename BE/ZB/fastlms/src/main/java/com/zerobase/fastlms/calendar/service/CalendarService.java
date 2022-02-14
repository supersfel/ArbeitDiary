package com.zerobase.fastlms.calendar.service;

import java.time.LocalDateTime;
import java.util.List;

import com.zerobase.fastlms.calendar.dto.CalendarDto;
import com.zerobase.fastlms.calendar.entity.Calendar;
import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;
import com.zerobase.fastlms.project.entity.Project;

public interface CalendarService {
	public boolean makeCalendar(Long id, Project project);
	public String formatDtToStringSplitPoint(LocalDateTime regDt);
	public Date makeDate(Calendar calendar, LocalDateTime now, int days);
	public boolean makeAllDate();
	
	public boolean ex(Long projectId);
	CalendarDto addWorkTime(CalendarDto calendarDto);
}
