package com.zerobase.fastlms.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.fastlms.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	void deleteAllByCalendarId(Long calendarId);
	
}
