package com.zerobase.fastlms.calendar.model.responseDto;

import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;

import lombok.Getter;

@Getter
public class FixedTimes{
	String dayId;
	String worktime;
	
	public FixedTimes() {}
	public FixedTimes(FixedTimeInterface time) {
		this.dayId = time.getDayId();
		this.worktime = time.getWorktime();
	}
}