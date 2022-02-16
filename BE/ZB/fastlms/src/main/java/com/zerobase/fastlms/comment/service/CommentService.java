package com.zerobase.fastlms.comment.service;

public interface CommentService {
	boolean add(Long calendarId,String userId, Long dateId, String text);
}
