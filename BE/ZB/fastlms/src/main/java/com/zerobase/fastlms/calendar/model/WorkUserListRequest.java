package com.zerobase.fastlms.calendar.model;

import java.util.ArrayList;
import java.util.List;
import com.zerobase.fastlms.calendar.dto.CalendarDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class WorkUserListRequest {
	Long projectId;
	Long calendarId;
	String projectName;
	List<UserListsDto> userList;
	List<DatesDto> dates;
}


