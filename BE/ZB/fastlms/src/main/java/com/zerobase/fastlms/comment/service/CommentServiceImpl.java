package com.zerobase.fastlms.comment.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.repository.DateRepository;
import com.zerobase.fastlms.comment.CommentRepository;
import com.zerobase.fastlms.comment.entity.Comment;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
	private final CommentRepository commentRepository;
	private final DateRepository dateRepository;
	private final MemberRepository memberRepository;
	@Override
	public boolean add(Long calendarId, String userId, Long dateId, String text) {
		// TODO Auto-generated method stub
		Optional<Member> member = memberRepository.findById(userId);
		if(!member.isPresent()) {
			System.out.println("회원 존재 X");
			return false;
		}
		Optional<Date> date = dateRepository.findById(dateId);
		if(!date.isPresent()) {
			System.out.println("날짜 존재 X");
			return false;
		}
		System.out.println(date.get().getDateId());
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		Comment comment = Comment.builder()
				.time(LocalDateTime.now().format(formatter).toString())
				//.member(member.get())
				.userId(userId)
				.text(text)
				.calendarId(calendarId)
				.userName(member.get().getUserId())
				.date(date.get())
				.build();
		
		commentRepository.save(comment);
		return true;
	}

}
