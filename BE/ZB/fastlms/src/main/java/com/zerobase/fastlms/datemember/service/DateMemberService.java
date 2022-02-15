package com.zerobase.fastlms.datemember.service;

import java.util.List;

import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.calendar.model.CalendarUserList.userList;
import com.zerobase.fastlms.calendar.model.DatesDto;
import com.zerobase.fastlms.calendar.model.WorkUserListRequest;
import com.zerobase.fastlms.calendar.model.responseDto.UserLists;

public interface DateMemberService {
	List<Date> exceptAutoTime(Long projectId, Long caLendarId);

	boolean loadDailyWork(Long calendarId, List<DatesDto> userList);
	boolean deleteCommentAndDateMember(Long calendarId);
	boolean updateDailyWork(Long calendarId, List<UserLists> userList, List<Date> beforeDates);
}
