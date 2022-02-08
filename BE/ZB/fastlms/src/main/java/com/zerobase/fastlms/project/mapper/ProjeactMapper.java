package com.zerobase.fastlms.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.fastlms.project.dto.ProjectDto;
import com.zerobase.fastlms.project.model.ProjectParam;

@Mapper
public interface ProjeactMapper {
	List<ProjectDto> selectList(ProjectParam projectParam);
	long selectListCount(ProjectParam projectParam);
}
