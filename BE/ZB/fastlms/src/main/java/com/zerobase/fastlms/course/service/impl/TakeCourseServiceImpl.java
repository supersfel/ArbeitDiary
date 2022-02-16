package com.zerobase.fastlms.course.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.mapper.TakeCourseMapper;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParam;
import com.zerobase.fastlms.course.repository.TakeCourseRepository;
import com.zerobase.fastlms.course.service.TakeCourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService {
	private final TakeCourseMapper takeCourseMapper;
	private final TakeCourseRepository takeCourseRepository;
	
	@Override
	public List<TakeCourseDto> list(TakeCourseParam takeCourseParam) {
		// TODO Auto-generated method stub
		long totalCount = takeCourseMapper.selectListCount(takeCourseParam);
		System.out.println("hi");
		List<TakeCourseDto> list = takeCourseMapper.selectList(takeCourseParam);
		System.out.println("??");
		if(!CollectionUtils.isEmpty(list)) {
			int i=0;
			for(TakeCourseDto x : list) {
				x.setTotalCount(totalCount);
				x.setCourseNum(totalCount - takeCourseParam.getPageStart() - i);
				i++;
			}
		}
		return list;
	}


	@Override
	public ServiceResult updateStatus(long id, String status) {
		// TODO Auto-generated method stub
		Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
		System.out.println(optionalTakeCourse);
		if(!optionalTakeCourse.isPresent()) {
			
			return new ServiceResult(false, "정보 존재 X");
		}
		TakeCourse takeCourse =optionalTakeCourse.get();
		
		takeCourse.setStatus(status);
		takeCourseRepository.save(takeCourse);
		
		return new ServiceResult(true);
	}

}
