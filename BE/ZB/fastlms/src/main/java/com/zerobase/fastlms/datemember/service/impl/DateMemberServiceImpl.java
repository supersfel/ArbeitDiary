package com.zerobase.fastlms.datemember.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.zerobase.fastlms.calendar.dto.CalendarDto;
import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.calendar.model.DateUserDto;
import com.zerobase.fastlms.calendar.model.DatesDto;
import com.zerobase.fastlms.calendar.model.DayIssues;
import com.zerobase.fastlms.calendar.model.WorkUserListRequest;
import com.zerobase.fastlms.calendar.model.responseDto.FixedTimes;
import com.zerobase.fastlms.calendar.model.responseDto.UserLists;
import com.zerobase.fastlms.calendar.repository.DateRepository;
import com.zerobase.fastlms.calendar.service.CalendarService;
import com.zerobase.fastlms.comment.CommentRepository;
import com.zerobase.fastlms.comment.entity.Comment;
import com.zerobase.fastlms.datemember.entity.DateMember;
import com.zerobase.fastlms.datemember.repository.DateMemberRepository;
import com.zerobase.fastlms.datemember.service.DateMemberService;
import com.zerobase.fastlms.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DateMemberServiceImpl implements DateMemberService{
	private final static int TIMELENGTH = 48;
	private final ProjectService projectService;
	private final CalendarService calendarService;
	
	private final DateMemberRepository dateMemberRepository;
	private final DateRepository dateRepository;
	private final CommentRepository commentRepository;
	
	
	public int[] getTodayDay(){
		String now = formatDtToString(LocalDateTime.now());
		int[] day = new int[3]; 
		day[0] = Integer.parseInt(now.substring(0,2));
		day[1] = Integer.parseInt(now.substring(2,4));
		day[2] = Integer.parseInt(now.substring(4));
		return day;
	}
	
	public boolean isWorkTime(String workTime) {
		for(char c : workTime.toCharArray()) {
			if(c == '1') {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Date> exceptAutoTime(Long projectId, Long calendarId){		
		int[] day = getTodayDay();
		Optional<Date> today = dateRepository.findByCalendarCalendarIdAndYearAndMonthAndDay(calendarId, day[0], day[1], day[2]);
		System.out.println("Heeo");

		List<Date> dates = dateRepository.findAllByCalendarIdAndDateId(calendarId, today.get().getDateId());		
		CalendarUserList calendarUserList = projectService.getUserList(projectId);
		CalendarDto calendarDto = new CalendarDto(calendarUserList); 
		CalendarDto result = calendarService.addWorkTime(calendarDto);
		List<UserLists> userList = result.getUserList();

		List<Date> dateWork = new ArrayList<Date>();
		List<DateMember> deleteWork = new ArrayList<DateMember>();
		for(Date date : dates) {
			List<DateMember> dayWorkers = date.getDateMember();
			if(dayWorkers.isEmpty()) {
				// 근문자가 없음
				dateWork.add(date);
				continue;
			}
			
			/*
			 * 근무자들 : 대타 + 고정 근무자
			 */
			List<DateMember> newWorker = new ArrayList<DateMember>();
			for(DateMember worker : dayWorkers) {
				System.out.println("[DateMember ID] : "+worker.getId());
				boolean isCoverWorker = true;
				// 고정 근무자들 확인
				for(UserLists user : userList) {
					System.out.println(worker.getUserId()+":"+user.getUserId());
					if(!worker.getUserId().equals(user.getUserId())) {
						System.out.println("ㅈ같네");
						//근무자들과 고정 근무자가 다름 = 대타자 놔둠
						continue;
					}
					
					
					//같으면
					isCoverWorker = false;
					for(FixedTimes time : user.getFixedtimes()) {
						System.out.println("[before]" + time.getWorktime() + ":" + worker.getWorkTime());
						System.out.println(time.getDayId() + ":" + date.getDayOfWeek());
						if(!time.getDayId().equals(date.getDayOfWeek())){
							// 해당 날짜 아님
							continue;
						}
						if(!isWorkTime(time.getWorktime())) {
							//일 하지 않는 날
							continue;
						}
						System.out.println("[일하네요]");
						// 일 하는 날
						String compWorkTime = time.getWorktime();
						StringBuilder resetWorkTime = new StringBuilder(worker.getWorkTime());
						boolean isAddTime = false;
						for(int i=0; i < TIMELENGTH; i++) {
							if(compWorkTime.charAt(i) == '1') {
								resetWorkTime.setCharAt(i, '0');
							} else if(resetWorkTime.charAt(i) == '1') {
								isAddTime = true;
							}
						}
						System.out.println("[After]" + resetWorkTime);
						if(isAddTime) {
							worker.setWorkTime(resetWorkTime.toString());
							newWorker.add(worker);
						}
						deleteWork.add(worker);
						break;
					}
					break;
				}	
				if(isCoverWorker) {
					//대타자
					newWorker.add(worker);
					deleteWork.add(worker);
				}	
			}
			date.setDateMember(newWorker);
			dateWork.add(date);
		}
		
		dateMemberRepository.deleteAll(deleteWork);
		return dateWork;
	}
		
	@Override
	public boolean updateDailyWork(Long calendarId, List<UserLists> userList, List<Date> beforeDates) {
		int[] day = getTodayDay();
		System.out.println("[day 받아오기 성공] : "+day);
		Optional<Date> todayDate = dateRepository.findByCalendarCalendarIdAndYearAndMonthAndDay(calendarId, day[0], day[1], day[2]);
		Long today = todayDate.get().getDateId();
		System.out.println("[오늘 날짜 받아오기 성공] : "+today);
		
		/*
		 * 업데이트 멤버
		 */
		System.out.println("===== 시작 =====");
		List<Date> uploadDate = new ArrayList<Date>();
		List<DateMember> uploadMember = new ArrayList<>();	
		for(Date date : beforeDates) {
			List<DateMember> dayWorkers = date.getDateMember();
			System.out.println("[날짜 ID]"+ date.getDateId());
			System.out.println(date.getDayOfWeek());
			
			// 새로 갱신된 고정 근무자 추가
			List<DateMember> todayWorker = new ArrayList<>();
			for(UserLists user : userList) {
				System.out.println(user.getUserId());
				
				for(FixedTimes time : user.getFixedtimes()) {
					System.out.println(time.getDayId());
					if(!time.getDayId().equals(date.getDayOfWeek())) {
						System.out.println("해당 요일이 아니다.");
						continue;
					}
					if(!isWorkTime(time.getWorktime())) {
						System.out.println("time :" + time.getWorktime());
						System.out.println("일하는 날이 아님");
						//일 하지 않는 날
						continue;
					}
					
					boolean isAddWorker = false;
					for(DateMember worker : dayWorkers) {
						if(!worker.getUserId().equals(user.getUserId())) {
							continue;
						}
						
						isAddWorker = true;
						String compWorkTime = time.getWorktime();
						StringBuilder resetWorkTime = new StringBuilder(worker.getWorkTime());
						for(int i=0; i < TIMELENGTH; i++) {
							if(compWorkTime.charAt(i) == '1') {
								resetWorkTime.setCharAt(i, '1');
							}
						}
						worker.setWorkTime(resetWorkTime.toString());
						System.out.println(worker);
						todayWorker.add(worker);
						uploadMember.add(worker);
						break;
					}
					if(!isAddWorker) {
						DateMember dateMember = new DateMember().builder()
								.date(date)
								.workTime(time.getWorktime())
								.userId(user.getUserId())
								.userName(user.getName())
								.calendarId(calendarId)
								.build(); 
						uploadMember.add(dateMember);
					}
					break;
				}
			}
			for(DateMember worker : dayWorkers) {
				boolean isTodayWork = false;
				for(DateMember todayWork : todayWorker) {
					if(worker.getUserId().equals(todayWork.getUserId())) {
						isTodayWork = true;
						break;
					}
				}
				if(!isTodayWork) {
					DateMember dateMember = new DateMember().builder()
							.date(date)
							.workTime(worker.getWorkTime())
							.userId(worker.getUserId())
							.userName(worker.getUserName())
							.calendarId(calendarId)
							.build(); 
					uploadMember.add(dateMember);
				}
			}
		}
			
			
			
//			if(dayWorkers.isEmpty()) {
//				System.out.println("근무자 무");
//				for(UserLists user : userList) {
//					System.out.println(user.getUserId());
//					for(FixedTimes time : user.getFixedtimes()) {
//						System.out.println(time.getDayId());
//						if(!time.getDayId().equals(date.getDayOfWeek())) {
//							System.out.println("해당 요일이 아니다.");
//							continue;
//						}
//						if(!isWorkTime(time.getWorktime())) {
//							System.out.println("time :" + time.getWorktime());
//							System.out.println("일하는 날이 아님");
//							//일 하지 않는 날
//							continue;
//						}
//						
//						//일하는 날
//						DateMember dateMember = new DateMember().builder()
//								.date(date)
//								.workTime(time.getWorktime())
//								.userId(user.getUserId())
//								.userName(user.getName())
//								.calendarId(calendarId)
//								.build(); 
//						uploadMember.add(dateMember);
//						break;
//					}
//				}
//				continue;
//			}
//			
//			System.out.println("근무자 유");
//			for(DateMember worker : dayWorkers) {
//				System.out.println("[유저 ID]"+ worker.getUserId());
//				//boolean isCoverWorker = true;
//				// 고정 근무자들 확인
//				boolean isCoverWorker = true;
//				for(UserLists user : userList) {
//					if(!worker.getUserId().equals(user.getUserId())) {
//						//근무자들과 고정 근무자가 다름 = 대타자 놔둠
//						continue;
//					}
//					
//					//같으면
//					isCoverWorker = false;
//					for(FixedTimes time : user.getFixedtimes()) {
//						if(!isWorkTime(time.getWorktime())) {
//							//일 하지 않는 날
//							continue;
//						}
//						
//						// 일 하는 날
//						
//						// 추가 근무 존재
//						if(worker.getUserId().equals(user.getUserId())) {
//							String compWorkTime = time.getWorktime();
//							StringBuilder resetWorkTime = new StringBuilder(worker.getWorkTime());
//							for(int i=0; i < TIMELENGTH; i++) {
//								if(compWorkTime.charAt(i) == '1') {
//									resetWorkTime.setCharAt(i, '1');
//								}
//							}
//							worker.setWorkTime(resetWorkTime.toString());
//							System.out.println("====================================");
//							System.out.println(worker);
//							uploadMember.add(worker);
//							break;
//						}
//						
//						// 추가 근무 존재 X
//						DateMember dateMember = new DateMember().builder()
//								.date(date)
//								.workTime(time.getWorktime())
//								.userId(user.getUserId())
//								.userName(user.getName())
//								.calendarId(calendarId)
//								.build(); 
//						uploadMember.add(dateMember);
//						break;
//					}
//					break;
//				}	
//				if(isCoverWorker) {
//					//대타자
//					uploadMember.add(worker);
//				}	
//			}
//			date.setDateMember(uploadMember);
//			uploadDate.add(date);
//			
//		}
		System.out.println("================");
		dateMemberRepository.saveAll(uploadMember);
		//dateRepository.saveAll(uploadDate);
		
		return true;
	}
	
	
	@Transactional
	@Override
	public boolean loadDailyWork(Long calendarId, List<DatesDto> userList) {
		List<Date> dates = dateRepository.findAllByCalendarId(calendarId);
		
		System.out.println(userList.isEmpty());
		/*
		 * 로드 멤버
		 */
		List<DateMember> uploadMember = new ArrayList<>();
		List<Comment> uploadComment = new ArrayList<>();

		
		for(Date date : dates) {
			for(DatesDto userInfo : userList) {
				if(!date.getDateId().equals(userInfo.getDateId())) {
					System.out.println(date.getDateId()+" : "+userInfo.getDateId());
					continue;
				}
				System.out.println("진입");
				for(DateUserDto user : userInfo.getUsers()) {
					uploadMember.add(DateMember.builder()
							.date(date)
							.workTime(user.getWorktime())
							.userId(user.getUserId())
							.userName(user.getName())
							.calendarId(calendarId)
							.build());
				}
				
				for(DayIssues comment : userInfo.getDayIssues()) {
					uploadComment.add(Comment.builder()
							.time(comment.getTime())
							.text(comment.getText())
							.userName(comment.getName())
							.calendarId(calendarId)
							.date(date)
							.userId(comment.getUserId())
							.build());
				}
			}
		}
		
		System.out.println("======================================================");
		System.out.println(uploadComment.isEmpty());
		System.out.println(uploadMember.isEmpty());
		System.out.println("======================================================");
		deleteCommentAndDateMember(calendarId);
		saveAllCommentAndDateMember(uploadComment, uploadMember);
		
		return true;
	}
	
	@Transactional
	public boolean saveAllCommentAndDateMember(List<Comment> uploadComment, List<DateMember> uploadMember) {
		commentRepository.saveAll(uploadComment);
		dateMemberRepository.saveAll(uploadMember);	
		return true;
	}
	
	@Transactional
	@Override
	public boolean deleteCommentAndDateMember(Long calendarId) {
		commentRepository.deleteAllByCalendarId(calendarId);
		dateMemberRepository.deleteAllByCalendarId(calendarId);
		return true;
	}
	
	
	public String formatDtToString(LocalDateTime regDt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
		return regDt != null ? regDt.format(formatter) : "";
	}
	
	public String updateTime(String last, String now) {
		return null;
	}
}
