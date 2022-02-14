package com.zerobase.fastlms.calendar.model;

import java.util.List;

import lombok.Getter;

@Getter
public class DatesDto {
	String date;
	Long dateId;
	List<DateUserDto> users;
	List<DayIssues> dayIssues;
}
