package com.zerobase.fastlms.member.service;

import java.util.List;

import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;
import com.zerobase.fastlms.calendar.model.UserListsDto;
import com.zerobase.fastlms.calendar.model.WorkUserListRequest;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.SelectAutoSelf;

public interface WorkService {
	boolean testAdd(String day, String userId, Long projectId, String select);
	boolean updateUserList(WorkUserListRequest userList, List<Date> beforeUserList, String userId);
	boolean add(UserListsDto user, Long projectId, String select);
	boolean initWorkdays(Member member, Long projectId);
	
	public String getWorkTime();
	List<FixedTimeInterface> getWorkTimeWProjectId(String projectId, String userId);
}
