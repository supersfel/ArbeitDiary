package com.zerobase.fastlms.calendar.model.responseDto;

import java.util.ArrayList;
import java.util.List;

import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;

import lombok.Getter;

@Getter
public class UserLists{
	String userId;
	String name;
	List<FixedTimes> fixedtimes = new ArrayList<>();
	
	public UserLists() {}
	public UserLists(CalendarUserList.userList user) {
		this.userId = user.getUserId();
		this.name = user.getUserName();
		
		//setFixeditmes(user.getFixedtimes(), projectId);
	}
	
	public void setFixeditmes(List<FixedTimeInterface> times) {
		for(FixedTimeInterface time : times) {
			this.fixedtimes.add(new FixedTimes(time));
		}
	}
}