package com.zerobase.fastlms.calendar.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.comment.entity.Comment;

public interface CalendarInterface {
	@Value("#{target.calendarId}")
	String getCalendarId(); 	
	
	@Value("#{target.dates}")
	List<CustomDate> getDates();
	interface CustomDate{
		String getDateId();
		
		@Value("#{(target.year < 10 ? '0' + target.year : target.year) "
				+ "+ (target.month < 10 ? '0' + target.month : target.month)"
				+ "+ (target.day < 10 ? '0'+target.day : target.day)}")
		String getDate();
		
		@Value("#{target.dayOfWeek}")
		String getdayId();
		
		@Value("#{target.dateMember}")
		List<Users> getUsers();
		interface Users{
			@Value("#{target.workTime}")
			String getWorktime();
			String getUserId();
			
			@Value("#{target.userName}")
			String getName();
		}
		
		@Value("#{target.comments}")
		List<CustomComment> getDayIssues();	
		interface CustomComment{
			@Value("#{target.userId}")
			String getUserId();
			
			@Value("#{target.userName}")
			String getName();
			
			@Value("#{target.text}")
			String getText();
			
			@Value("#{target.time}")
			String getTime();
		}
	}
	
	
}
