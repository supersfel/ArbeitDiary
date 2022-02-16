package com.zerobase.fastlms.comment.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zerobase.fastlms.comment.model.CommentInput;
import com.zerobase.fastlms.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	private final CommentService commentService;
	
	@GetMapping("/comment")
	public String comment() {
		return "comment/comment";
	}
	
	@PostMapping("/comment")
	public String submitComment(CommentInput commentInput, Principal principal) {
		System.out.println("Comment : " + commentInput);
		boolean result = commentService.add(commentInput.getCalendarId(), principal.getName(),commentInput.getDateId(), commentInput.getText());
		return "comment/comment";
	}
}
