package com.zerobase.fastlms.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;
import com.zerobase.fastlms.calendar.model.FixedTimesDto;
import com.zerobase.fastlms.calendar.model.UserListsDto;
import com.zerobase.fastlms.calendar.model.WorkUserListRequest;
import com.zerobase.fastlms.calendar.model.enuum.DayEnum;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.Work;
import com.zerobase.fastlms.member.model.SelectAutoSelf;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.repository.WorkRepository;
import com.zerobase.fastlms.member.service.WorkService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WorkServiceImpl implements WorkService{
	private final WorkRepository workRepository;
	private final MemberRepository memberRepository;
	
	@Override
	public boolean testAdd(String day, String userId, Long projectId, String select) {
		// TODO Auto-generated method stub
		Optional<Work> optionalWork = workRepository.findByUserIdAndProjectIdAndDayAndPa(userId, projectId, day, select.toString());
		if(!optionalWork.isPresent()) {
			System.out.println("멤버 근무 시간이 아닙니다.");
			Optional<Member> optionalMember = memberRepository.findById(userId);
			if(!optionalMember.isPresent()) {
				System.out.println("[현재 멤버가 존재하지 않습니다.]");
				return false;
			}
			Work work = Work.builder()
					.workDay(day)
					.workTime("000000000000000000000000000000000000000011111111")
					.workTimeActive(select.toString())
					.projectId(projectId)
					.member(optionalMember.get())
					.build();
			workRepository.save(work);
			return true;
		}
		System.out.println("기존 업데이트");
		Work work = optionalWork.get();
		work.setWorkTime("000000000000000000011111111000000000000000000000");
		workRepository.save(work);
		return true;
	}
	
	@Override
	public boolean add(UserListsDto user, Long projectId, String select) {
		// TODO Auto-generated method stub
		if(user.getFixedtimes().isEmpty()) {
			System.out.println("없다.");
			return false;
		}
		
		for(FixedTimesDto time : user.getFixedtimes()) {
			Optional<Work> optionalWork = workRepository.findByUserIdAndProjectIdAndWorkDay(user.getUserId(), projectId, time.getDayId());
			System.out.println(user.getUserId());
			if(!optionalWork.isPresent()) {
				System.out.println("새 WORK ID 추가");
				Optional<Member> optionalMember = memberRepository.findById(user.getUserId());
				
				if(!optionalMember.isPresent()) {
					System.out.println("[현재 멤버가 존재하지 않습니다.]");
					return false;
				}
				Work work = Work.builder()
						.workDay(time.getDayId())
						.workTime(time.getWorktime())
						.workTimeActive(select.toString())
						.projectId(projectId)
						.member(optionalMember.get())
						.build();
				workRepository.save(work);
				continue;
			}
			System.out.println("기존 업데이트");
			Work work = optionalWork.get();
			work.setWorkTime(time.getWorktime());
			workRepository.save(work);
		}
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
		return true;
	}
	
	@Override
	public boolean updateUserList(WorkUserListRequest userList, List<Date> beforeUserList, String userId) {
		if(userList.getUserList().isEmpty()) {
			System.out.println("존재하지 않습니다.");
			return false;
		}
		Long projectId = userList.getProjectId();
		for(UserListsDto user : userList.getUserList()) {
			boolean result = add(user,projectId,SelectAutoSelf.AUTO.toString());
			System.out.println(result);
		}
		return true;
	}

	@Override
	public String getWorkTime() {
		return "000000000000000000000000000000000000000000000000";
	}
	
	@Override
	public boolean initWorkdays(Member member, Long projectId) {
		List<Work> workList = new ArrayList<>();
		DayEnum[] days = DayEnum.values();
		for(DayEnum day : days) {
			workList.add(Work.builder()
					.workDay(day.name())
					.workTime(getWorkTime())
					.workTimeActive(SelectAutoSelf.AUTO.toString())
					.member(member)
					.projectId(projectId)
					.build());  
		}
		workRepository.saveAll(workList);
		return true;
	}
	
	@Override
	public List<FixedTimeInterface> getWorkTimeWProjectId(String projectIdText, String userId) {
		Long projectId = Long.parseLong(projectIdText);
		return workRepository.findByProjectIdAndMember_UserId(projectId, userId);
	}
}
