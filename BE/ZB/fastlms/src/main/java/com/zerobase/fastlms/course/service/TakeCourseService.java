package com.zerobase.fastlms.course.service;

import java.util.List;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParam;

public interface TakeCourseService {
	List<TakeCourseDto> list(TakeCourseParam takeCourseParam);
	ServiceResult updateStatus(long id, String status);
}
