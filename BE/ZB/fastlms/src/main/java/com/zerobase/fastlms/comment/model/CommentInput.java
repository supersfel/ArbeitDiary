package com.zerobase.fastlms.comment.model;

import lombok.Data;

@Data
public class CommentInput {
	Long calendarId;
	Long dateId;
	String text;
}
