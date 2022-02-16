package com.zerobase.fastlms.course.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.common.model.ResponseResult;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.repository.CourseRepository;
import com.zerobase.fastlms.course.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiCourseController {
	private final CourseService courseService;
	private final CategoryService categoryService;
	
	@PostMapping("/api/course/req.api")
	public ResponseEntity<?> courseReq(Model model,
			@RequestBody TakeCourseInput takeCourseInput,
			Principal principal) {
		takeCourseInput.setUserId(principal.getName());
		ServiceResult result = courseService.req(takeCourseInput);
		if(!result.isResult()) {
			System.out.println("??");
			ResponseResult responseResult = new ResponseResult(false, result.getMessage());
			return ResponseEntity.ok().body(responseResult);
		}
		ResponseResult responseResult = new ResponseResult(true);
		return ResponseEntity.ok().body(responseResult);
	}
}
